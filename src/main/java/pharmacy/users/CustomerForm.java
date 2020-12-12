package pharmacy.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

class CustomerForm {
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String street;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String houseNumber;

	@NotNull(message = "{DeliveryForm.email.NotEmpty}")
	private final Number postCode;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String city;
	
	private final Boolean privateInsurance;

	public CustomerForm(String street, String houseNumber, Number postCode, String city, Boolean privateInsurance) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.privateInsurance = privateInsurance;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public Number getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}
}
