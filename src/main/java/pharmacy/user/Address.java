package pharmacy.user;

import javax.persistence.Embeddable;
/**
 * Adressen Klasse
 * @author Timon Trettin
 */
@Embeddable
public class Address {

	private String name;
	private String street;
	private String postCode;
	private String city;

	public Address() {}

	public Address(String name, String street, String postCode, String city) {
		this.name = name;
		this.street = street;
		this.postCode = postCode;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String newStreet) {
		this.street = newStreet;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String newPostCode) {
		this.postCode = newPostCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String newCity) {
		this.city = newCity;
	}
}
