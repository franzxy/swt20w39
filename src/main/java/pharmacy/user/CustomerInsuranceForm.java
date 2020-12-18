package pharmacy.user;

class CustomerInsuranceForm {
	
	private final Boolean privateInsurance;

	public CustomerInsuranceForm(Boolean privateInsurance) {
		this.privateInsurance = privateInsurance;
	}

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}
}
