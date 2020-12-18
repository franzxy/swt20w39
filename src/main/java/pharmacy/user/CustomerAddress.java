package pharmacy.user;

public class CustomerAddress {

	private String street;
	private String houseNumber;
	private Number postCode;
	private String city;
	
	public CustomerAddress(String street, String houseNumber, Number postCode, String city) {
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
