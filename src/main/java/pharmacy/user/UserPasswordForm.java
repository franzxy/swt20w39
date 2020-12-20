package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.type.TrueFalseType;
import org.salespointframework.useraccount.UserAccountManagement;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.validation.constraints.Pattern;	

class UserPasswordForm {
	
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	private String oldPassword;

	public final UserAccountManagement userAccounts;

	@AssertTrue(message="Old password incorrect")
	public boolean isValid() {
		var name = SecurityContextHolder.getContext().getAuthentication().getName();
		var account = userAccounts.findByUsername(name);
		return this.oldPassword.equals(UnencryptedPassword.of(account.get().getPassword().toString()));
/*
	@AssertTrue(message="Old password incorrect")
	private boolean isValid() {
		System.out.println(oldPassword);
		System.out.println(UserManagement.getPassword);
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
		return oldPassword.equals(SecurityContextHolder.getContext().getAuthentication().getCredentials());
	}
*/
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

	@AssertTrue(message="Passwörter stimmen nicht überein.")
	private boolean isConfirm() {
		return newPassword.equals(confirmPassword);
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
