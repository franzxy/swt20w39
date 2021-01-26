package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

class BankAccountForm {

	@NotEmpty(message = "Name fehlt")
	private final String name;
	
	@NotEmpty(message = "IBAN fehlt")
	@Pattern(regexp="^[A-Z]{2}(?:[ ]?[0-9]){18,20}$", message = "Das ist keine IBAN")
	private final String iban;

	@NotEmpty(message = "BIC fehlt")
	@Pattern(regexp="^[0-9]*$", message = "Gehalt darf nur aus Zahlen bestehen")
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
