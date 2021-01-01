package pharmacy.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESSES")
public class Address implements Serializable{

	private @Id @GeneratedValue long id;

	private String name;
	private String street;
	private Long postCode;
	private String city;
	
	public Address(String name, String street, Long postCode, String city) {
		this.street = street;
		this.postCode = postCode;
		this.city = city;
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
