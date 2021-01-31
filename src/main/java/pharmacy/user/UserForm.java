package pharmacy.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

/**
 * Nutzer Registrierung Formular
 * @author Timon Trettin
 */
class UserForm {
	
	@NotEmpty(message = "Benutzername fehlt")
	@ValidUserName(message = "Benutzername wird schon verwendet")
	private final String name;

	@NotEmpty(message = "Passwort fehlt")
	@Size(min = 8, max = 128, message = "Passwort muss aus 8-120 Zeichen bestehen")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "Passwort muss Kleinbuchstaben enthalten")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "Passwort muss Großbuchstaben enthalten")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "Passwort muss Sonderzeichen enthalten")
	@Pattern(regexp="^[\\S]+$", message = "Passwort darf kein Leerzeichen enthalten")
	private final String password;

	@NotEmpty(message = "Passwort Bestätigen fehlt")
	// @ValidPassword(comp = password)
	private final String confirmPassword;

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
