package pharmacy.user;

import javax.persistence.Embeddable;

@Embeddable
public class Insurance {

	private String company;
	private Long insuranceNumber;

	public Insurance() {}

	public Insurance(String company, Long insuranceNumber) {
		this.company = company;
		this.insuranceNumber = insuranceNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setName(String newCompany) {
		company = newCompany;
	}

	public Long getId() {
		return insuranceNumber;
	}

	public void setId(Long newInsuranceNumber) {
		insuranceNumber = newInsuranceNumber;
	}
}
