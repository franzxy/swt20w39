package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

class PasswordForm {
	
/*
	@NotEmpty(message = "Altes Passwort fehlt")
	@ValidPassword(message = "Altes Passwort stimmt nicht überein")
	private String oldPassword;
	
	@AssertTrue(message="Old password incorrect")
	private boolean isValid() {
		System.out.println(oldPassword);
		System.out.println(UserManagement.getPassword);
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
		return oldPassword.equals(SecurityContextHolder.getContext().getAuthentication().getCredentials());
	}
*/
	@NotEmpty(message = "Neues Passwort fehlt")
	@Size(min = 8, max = 128, message = "Passwort muss aus 8-120 Zeichen bestehen")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "Passwort muss Kleinbuchstaben enthalten")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "Passwort muss Großbuchstaben enthalten")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "Passwort muss Sonderzeichen enthalten")
	@Pattern(regexp="^[\\S]+$", message = "Passwort darf kein Leerzeichen enthalten")
	private final String newPassword;

	@NotEmpty(message = "Passwort Bestätigen fehlt")
	//@ValidPassword(pas = newPassword)
	private final String confirmPassword;

	public PasswordForm(String newPassword, String confirmPassword) {
		//this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
/*
	public String getOldPassword() {
		return oldPassword;
	}
*/
	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
}
