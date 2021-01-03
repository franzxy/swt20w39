package pharmacy.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameValidator implements ConstraintValidator<ValidUserName, String> {
	@Autowired
	private UserAccountManagement management;
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(this.management.findByUsername(value).isEmpty())
			return true;
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("{RegistrationForm.name.ValidUserName}").addConstraintViolation();
		return false;
	}
	

}
