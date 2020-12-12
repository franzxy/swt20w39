package pharmacy.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;

	// Customer
	private String street;
	private String houseNumber;
	private Number postCode;
	private String city;
	private Boolean privateInsurance;

	// Employee
	private Money salary;
	private Integer vacation;
	private Integer vacationRemaining;

	public User(UserAccount userAccount) {
		
		this.userAccount = userAccount;
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	// Customer
	public void addCustomer(String street, String houseNumber, Number postCode, String city, Boolean privateInsurance) {
		
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.privateInsurance = privateInsurance;
		userAccount.add(Role.of("CUSTOMER"));
	}

	public void removeCustomer() {

		userAccount.remove(Role.of("CUSTOMER"));
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

	// Doctor
	public void addDoctor() {

		userAccount.add(Role.of("DOCTOR"));
	}

	public void removeDoctor() {

		userAccount.remove(Role.of("DOCTOR"));
	}

	// Employee
	public void addEmployee(Money salary, Integer vacation) {

		this.salary = salary;
		this.vacation = vacation;
		this.vacationRemaining = vacation;
		userAccount.add(Role.of("EMPLOYEE"));
	}

	public void removeEmployee() {

		userAccount.remove(Role.of("EMPLOYEE"));
	}
	public Money getSalary() {
		return salary;
	}

	public void setSalary(Money newSalary) {
		this.salary = newSalary;
	}

	public Integer getVacation() {
		return vacation;
	}

	public void setVacation(Integer newVacation) {
		this.vacation = newVacation;
	}

	public Integer getVacationRemaining() {
		return vacationRemaining;
	}

	public void vacation(Integer duration) {
		this.vacationRemaining -= duration;
	}
}
