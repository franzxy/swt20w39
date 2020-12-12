package pharmacy.users;

import javax.validation.constraints.NotEmpty;
import org.javamoney.moneta.Money;

class EmployeeForm {
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final Money salary;
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final Integer vacation;

	public EmployeeForm(Money salary, Integer vacation) {

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
