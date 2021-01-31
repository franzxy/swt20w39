package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Formular zum überprüfen des Bank Kontos
 * @author Timon Trettin
 */
class BankAccountForm {

	@NotEmpty(message = "Name fehlt")
	private final String name;
	
	@Pattern(regexp="^[A-Z]{2}[0-9]{18,20}$", message = "Das ist keine IBAN")
	private final String iban;

	@Pattern(regexp="^([a-zA-Z]{4})([a-zA-Z]{2})(([2-9a-zA-Z]{1})([0-9a-np-zA-NP-Z]{1}))((([0-9a-wy-zA-WY-Z]{1})([0-9a-zA-Z]{2}))|([xX]{3})|)$", message = "Das ist keine BIC")
	private final String bic;

	public BankAccountForm(String name, String iban, String bic) {
		this.name = name;
		this.iban = iban;
		this.bic = bic;
	}

	public String getName() {
		return name;
	}

	public String getIban() {
		return iban;
	}

	public String getBic() {
		return bic;
	}
}
