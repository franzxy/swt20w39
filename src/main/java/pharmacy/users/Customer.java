package pharmacy.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Customer extends User {

	private String street;
	private String houseNumber;
	private Number postCode;
	private String city;

	private Boolean privateInsurance;

	public Customer(UserAccount userAccount, String street, String houseNumber, Number postCode, String city, Boolean privateInsurance) {

		super(userAccount);
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.privateInsurance = privateInsurance;
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

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}

	public void setPrivateInsurance(Boolean newInsurance) {
		this.privateInsurance = newInsurance;
	}
}
