package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

class AddressForm {

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String name;
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String street;

	@NotNull(message = "{DeliveryForm.email.NotEmpty}")
	private final Number postCode;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String city;

	public AddressForm(String name, String street, Number postCode, String city) {
		this.name = name;
		this.street = street;
		this.postCode = postCode;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public Number getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}
}
