package pharmacy.order;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

class CheckoutForm {
	
	@AssertTrue
	private final Boolean address;
	
	@NotEmpty(message = "Bezahlart fehlt")
	private final String payment;

	@ValidInsurance
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
