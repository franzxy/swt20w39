package pharmacy.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;	
import org.springframework.security.core.context.SecurityContextHolder;

class UserPasswordForm {
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	private String oldPassword;

	@AssertTrue(message="Old password incorrect")
	private boolean isValid() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
		return this.oldPassword.equals(SecurityContextHolder.getContext().getAuthentication().getCredentials());
	}

	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String newPassword;

	public UserPasswordForm(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
}
