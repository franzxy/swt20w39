package pharmacy.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESSES")
public class Address implements Serializable{

	private static final long serialVersionUID = -7114101035786254953L;
	private @Id @GeneratedValue long id;

	private String street;
	private String houseNumber;
	private Number postCode;
	private String city;
	
	public Address(String street, String houseNumber, Number postCode, String city) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String newStreet) {
		this.street = newStreet;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String newHouseNumber) {
		this.houseNumber = newHouseNumber;
	}

	public Number getPostCode() {
		return postCode;
	}

	public void setPostCode(Number newPostCode) {
		this.postCode = newPostCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String newCity) {
		this.city = newCity;
	}
}
