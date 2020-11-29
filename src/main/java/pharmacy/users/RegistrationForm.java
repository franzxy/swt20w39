package pharmacy.users;

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

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String password;

	@NotEmpty(message = "{RegistrationForm.address.NotEmpty}")
	private final String address;
	

	public RegistrationForm(String name, String password, String address) {
		this.name = name;
		this.password = password;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}
}
