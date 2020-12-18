package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

class CustomerAddressForm {
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String street;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String houseNumber;

	@NotNull(message = "{DeliveryForm.email.NotEmpty}")
	private final Number postCode;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String city;

	public CustomerAddressForm(String street, String houseNumber, Number postCode, String city) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
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
}
