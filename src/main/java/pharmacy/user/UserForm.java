package pharmacy.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

class UserForm {
	
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	@ValidUserName
	private final String name;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String password;

	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String confirmPassword;

	@AssertTrue(message="Passwörter stimmen nicht überein.")
	private boolean isConfirm() {
		return password.equals(confirmPassword);
	}

	public UserForm(String name, String password, String confirmPassword) {
		this.name = name;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
}
