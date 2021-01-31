package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Formular zum für Mitarbeiter Gehalt
 * @author Timon Trettin
 */
class EmployeeForm {

	@NotEmpty(message = "Gehalt fehlt")
	@Pattern(regexp="^[0-9]*$", message = "Gehalt darf nur aus Zahlen bestehen")
	private final String salary;

	public EmployeeForm(String salary) {
		this.salary = salary;
	}

	public String getSalary() {
		return salary;
	}
}
