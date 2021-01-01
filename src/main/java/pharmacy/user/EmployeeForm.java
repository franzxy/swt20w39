package pharmacy.user;

import java.util.function.LongFunction;

import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeForm {
	
	private final Long address;
	
	private final Long salary;
	
	private final Long vacation;

	public EmployeeForm(Long address, Long salary, Long vacation) {
		this.address = address;
		this.salary = salary;
		this.vacation = vacation;
	}

	public Long getAddress() {
		return address;
	}

	public Long getSalary() {
		return salary;
	}

	public Long getVacation() {
		return vacation;
	}
}
