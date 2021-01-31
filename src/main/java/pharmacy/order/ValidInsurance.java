package pharmacy.order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * Versicherungs Constraint
 * @author Timon Trettin
 */
@Documented
@Constraint(validatedBy = InsuranceValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidInsurance {
	
	    String message() default "Invalid Name";
	 
	    Class<?>[] groups() default {};
	 
	    Class<? extends Payload>[] payload() default {};
	
}
