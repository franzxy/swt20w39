package pharmacy.order;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

class InsuranceForm {
	
	@NotEmpty(message = "Krankenkasse fehlt")
	private final String company;

	@NotEmpty(message = "Versichertennummer fehlt")
	@Pattern(regexp="^[A-Z][0-9]{11}$", message = "Das ist keine Versichertennummer")
	private final String insuranceNumber;

	public InsuranceForm(String company, String insuranceNumber) {
		this.company = company;
		this.insuranceNumber = insuranceNumber;
	}

	public String getCompany() {
		return company;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}
}
