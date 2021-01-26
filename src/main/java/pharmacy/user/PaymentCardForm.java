package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

class PaymentCardForm {

	@NotEmpty(message = "Name fehlt")
	private final String name;
	
	@NotEmpty(message = "IBAN fehlt")
	@Pattern(regexp="^[A-Z]{2}(?:[ ]?[0-9]){18,20}$", message = "Das ist keine IBAN")
	private final String number;

	@NotEmpty(message = "BIC fehlt")
	@Pattern(regexp="^[0-9]*$", message = "Gehalt darf nur aus Zahlen bestehen")
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
