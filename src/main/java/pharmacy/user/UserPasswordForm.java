package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.springframework.security.core.context.SecurityContextHolder;

class UserPasswordForm {
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	private String oldPassword;

	@AssertTrue(message="Old password incorrect")
	public boolean isValid() {
		var password = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		System.out.println(password);
		System.out.println(password.toString());
		System.out.println(UnencryptedPassword.of(password.toString()));
		return this.oldPassword.equals(UnencryptedPassword.of(password.toString());
	}

	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String newPassword;

	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String confirmPassword;

	@AssertTrue(message="Old password incorrect")
	public boolean confirmValid() {
		System.out.println(UnencryptedPassword.of(newPassword));
		System.out.println(UnencryptedPassword.of(confirmPassword));
		return this.newPassword.equals(confirmPassword);
	}

	public UserPasswordForm(String oldPassword, String newPassword, String confirmPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
}
