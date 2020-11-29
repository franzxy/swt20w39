package pharmacy.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.salespointframework.useraccount.Password.UnencryptedPassword;

class PasswordForm {

	@NotEmpty(message = "{PasswordForm.oldPassword.NotEmpty}")
	// @MatchingPassword(SecurityContextHolder.getContext().getAuthentication().getCredentials(), message = "{PasswordForm.oldPassword.Correct}")
	private final String oldPassword;

	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String newPassword;

	@NotEmpty(message = "{PasswordForm.confirmPassword.NotEmpty}")
	// @MatchingPassword(newPassword, message = "{PasswordForm.confirmPassword.Match}")
	private final String confirmPassword;

	public PasswordForm(String oldPassword, String newPassword, String  confirmPassword) {
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

	public String getconfirmPassword() {
		return  confirmPassword;
	}
}
