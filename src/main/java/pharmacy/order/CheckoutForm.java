package pharmacy.order;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
/**
 * Formular zum überprüfen der Adresse, Bezahlmethode und Versicherung
 * @author Timon Trettin
 */
class CheckoutForm {
	
	@AssertTrue(message = "Adresse felt")
	private final Boolean address;
	
	@NotEmpty(message = "Bezahlart fehlt")
	private final String payment;

	@ValidInsurance(message = "Versicherung fehlt")
	private final Boolean insurance;

	public CheckoutForm(Boolean address, String payment, Boolean insurance) {
		this.address = address;
		this.payment = payment;
		this.insurance = insurance;
	}

	public Boolean getAddress() {
		return address;
	}

	public String getPayment() {
		return payment;
	}

	public Boolean getInsurance() {
		return insurance;
	}
}
