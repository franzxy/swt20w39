package pharmacy.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Password.UnencryptedPassword;

class EmployeeAddForm {
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final Money salary;
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final Integer vacation;

	public EmployeeAddForm(Money salary, Integer vacation) {

		this.salary = salary;
		this.vacation = vacation;
	}

	public Money getSalary() {
		return salary;
	}

	public Integer getVacation() {
		return vacation;
	}
}
