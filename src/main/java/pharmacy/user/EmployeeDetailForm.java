package pharmacy.user;

import java.util.function.LongFunction;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeDetailForm {
	
	private final Long salary, vacation, iban;

	public EmployeeDetailForm(Long salary, Long vacation, Long iban) {
		this.salary = salary;
		this.vacation = vacation;
		this.iban = iban;
	}

	public Long getSalary() {
		return salary;
	}

	public Long getVacation() {
		return vacation;
	}

	public Long getIban() {
		return iban;
	}
}
