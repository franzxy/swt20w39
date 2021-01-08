package pharmacy.user;

class InsuranceForm {
	
	private final String company;
	private final Long insuranceNumber;

	public InsuranceForm(String company, Long insuranceNumber) {
		this.company = company;
		this.insuranceNumber = insuranceNumber;
	}

	public String getCompany() {
		return company;
	}

	public Long getInsuranceNumber() {
		return insuranceNumber;
	}
}
