package pharmacy.user;

class InsuranceForm {
	
	private final String company;
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
