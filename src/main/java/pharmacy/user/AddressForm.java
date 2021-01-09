package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

class AddressForm {

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String name;
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String street;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	@Pattern(regexp="^[0-9]*$", message = "test")
	private final String postCode;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String city;

	public AddressForm(String name, String street, String postCode, String city) {
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

	public String getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}
}
