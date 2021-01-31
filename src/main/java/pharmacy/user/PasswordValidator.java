package pharmacy.user;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class PasswordValidator implements ConstraintValidator<ValidPassword, Object[]> {

	@Override
    public void initialize(ValidPassword constraintAnnotation) {}
	
	@Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
		return value[0].equals(value[1]);
    }
}
