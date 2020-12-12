package pharmacy.users;

import pharmacy.users.User.Insurance;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.salespointframework.useraccount.Password.UnencryptedPassword;

class RegistrationForm {
	
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String name;
	
	private final Insurance insuranceType;

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

	public RegistrationForm(String name, Insurance insuranceType, String email, String password) {
		this.name = name;
		this.insuranceType = insuranceType;
		this.email = email;
		this.password = password;
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
		return null;
	}

	public String getSalary() {
		return null;
	}

	public String getVacationRemaining() {
		return null;
	}
}
