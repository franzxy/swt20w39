package pharmacy.order;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class InsuranceValidator implements ConstraintValidator<ValidInsurance, Boolean> {
	@Autowired
	private OrderController controller;
	@Override
	public boolean isValid(Boolean value, ConstraintValidatorContext context) {
		if(this.controller.haspresonly(this.controller.getCart()) && !value) return false;
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("Versicherung fehlt").addConstraintViolation();
		return true;
	}
}
