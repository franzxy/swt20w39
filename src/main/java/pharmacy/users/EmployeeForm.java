package pharmacy.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.javamoney.moneta.Money;

class EmployeeForm {
	
	// User
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String name;
	
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String lastName;

	@NotEmpty(message = "{RegistrationForm.email.NotEmpty}")
	private final String email;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String password;
	
	// Employee
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final Money salary;
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final Integer vacation;

	public EmployeeForm(String name, String lastName, String email, String password, Money salary, Integer vacation) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.salary = salary;
		this.vacation = vacation;
	}

	// User
	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	// Employee
	public Money getSalary() {
		return salary;
	}

	public Integer getVacation() {
		return vacation;
	}
}
