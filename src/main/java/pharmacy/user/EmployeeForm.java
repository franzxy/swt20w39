package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

class EmployeeForm {
	
	@NotEmpty(message = "IBAN fehlt")
	@Pattern(regexp="^[A-Z]{2}(?:[ ]?[0-9]){18,20}$", message = "Das ist keine IBAN")
	private final String iban;

	@NotEmpty(message = "Gehalt fehlt")
	@Pattern(regexp="^[0-9]*$", message = "Gehalt darf nur aus Zahlen bestehen")
	private final String salary;

	public EmployeeForm(String iban, String salary) {
		this.iban = iban;
		this.salary = salary;
	}

	public String getIban() {
		return iban;
	}

	public String getSalary() {
		return salary;
	}
}
