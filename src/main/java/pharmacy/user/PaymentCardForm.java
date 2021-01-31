package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Kartenzahlung Formular
 * @author Timon Trettin
 */
class PaymentCardForm {

	@NotEmpty(message = "Name fehlt")
	private final String name;
	
	@Pattern(regexp="^[0-9]*$", message = "Das ist keine Kartennummer")
	private final String number;

	@Pattern(regexp="^[0-9]{3}$", message = "Das sind keine Sicherheitszahlen")
	private final String secure;

	public PaymentCardForm(String name, String number, String secure) {
		this.name = name;
		this.number = number;
		this.secure = secure;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getSecure() {
		return secure;
	}
}
