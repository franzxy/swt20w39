package pharmacy.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Formular zum überprüfen der Adresse
 * @author Timon Trettin
 */
class AddressForm {

	@NotEmpty(message = "Name fehlt")
	private final String name;
	
	@NotEmpty(message = "Straße und Hausnummer fehlt")
	private final String street;

	@NotEmpty(message = "Postleitzahl fehlt")
	@Pattern(regexp="^[0-9]*$", message = "Postleitzahl darf nur aus Zahlen bestehen")
	@Size(min = 5, max = 10, message = "Postleitzahl muss aus 5-10 Zeichen bestehen")
	private final String postCode;

	@NotEmpty(message = "Stadt fehlt")
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
