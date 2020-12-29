package pharmacy.user;

import java.util.function.LongFunction;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeForm {
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String street;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String houseNumber;

	private final Long postCode;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String city;
	
	private final Long iban;
	
	private final Long salary;
	
	private final Long vacation;

	public EmployeeForm(String street, String houseNumber, Long postCode, String city, Long iban, Long salary, Long vacation) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.iban = iban;
		this.salary = salary;
		this.vacation = vacation;
	}
	
	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public Long getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}

	public Long getIban() {
		return iban;
	}

	public Long getSalary() {
		return salary;
	}

	public Long getVacation() {
		return vacation;
	}
}
