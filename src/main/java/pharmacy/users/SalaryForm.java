package pharmacy.users;

import pharmacy.users.Customer.Insurance;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.salespointframework.useraccount.Password.UnencryptedPassword;

class SalaryForm {
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String name;
	
	private final Insurance insuranceType;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String email;

	@NotEmpty(message = "{DeliveryForm.password.NotEmpty}")
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String password;

	private final String address;

	public SalaryForm(String name, Insurance insuranceType, String email, String password, @Null String address) {
		this.name = name;
		this.insuranceType = insuranceType;
		this.email = email;
		this.password = password;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Insurance getInsuranceType() {
		return insuranceType;
	}

	public String getAddress() {
		return address;
	}
}
