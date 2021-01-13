package pharmacy.user;

import java.util.function.LongFunction;

import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeForm {
	
	@NotEmpty(message = "IBAN fehlt")
	@Pattern(regexp="^[A-Z]{2}(?:[ ]?[0-9]){18,20}$", message = "Das ist keine IBAN")
	private final String iban;

	@NotNull(message = "Gehalt fehlt")
	@Pattern(regexp="^[0-9]*$", message = "Gehalt darf nur aus Zahlen bestehen")
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
