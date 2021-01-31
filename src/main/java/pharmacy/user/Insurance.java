package pharmacy.user;

import javax.persistence.Embeddable;

/**
 * Versicherung Klasse
 * @author Timon Trettin
 */
@Embeddable
public class Insurance {

	private String company;
	private String insuranceNumber;

	public Insurance() {}

	public Insurance(String company, String insuranceNumber) {
		this.company = company;
		this.insuranceNumber = insuranceNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String newCompany) {
		company = newCompany;
	}

	public String getId() {
		return insuranceNumber;
	}

	public void setId(String newInsuranceNumber) {
		insuranceNumber = newInsuranceNumber;
	}
}
