package pharmacy.user;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable
public class Address {

	private String name;
	private String street;
	private Long postCode;
	private String city;

	public Address() {}

	public Address(String name, String street, Long postCode, String city) {
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

	public Long getPostCode() {
		return postCode;
	}

	public void setPostCode(Long newPostCode) {
		this.postCode = newPostCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String newCity) {
		this.city = newCity;
	}
}
