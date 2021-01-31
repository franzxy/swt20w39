package pharmacy.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validator für Nutzernamen
 * @author Timon Trettin
 */
public class UsernameValidator implements ConstraintValidator<ValidUserName, String> {
	@Autowired
	private UserAccountManagement management;
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(this.management.findByUsername(value).isEmpty())
			return true;
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate("Benutzername wird schon verwendet").addConstraintViolation();
		return false;
	}
	

}
