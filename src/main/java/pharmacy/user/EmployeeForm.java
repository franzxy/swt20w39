package pharmacy.user;

import java.util.function.LongFunction;

import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeForm {
	
	private final String iban;
	private final Long salary;

	public EmployeeForm(String iban, Long salary) {
		this.iban = iban;
		this.salary = salary;
	}

	public String getIban() {
		return iban;
	}

	public Long getSalary() {
		return salary;
	}
}
