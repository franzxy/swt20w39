package pharmacy.user;

import java.util.function.LongFunction;

import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeForm {
	
	private final Long salary;
	
	private final Long vacation;

	public EmployeeForm(Long salary, Long vacation) {
		this.salary = salary;
		this.vacation = vacation;
	}

	public Long getSalary() {
		return salary;
	}

	public Long getVacation() {
		return vacation;
	}
}
