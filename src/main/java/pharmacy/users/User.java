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
	public UserAccount userAccount;

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

	@SuppressWarnings("unused")
	private User() {}

	public User(UserAccount userAccount) {
		
		this.userAccount = userAccount;
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void addRole(Role role) {
		userAccount.add(role);
	}

	public void removeRole(Role role) {
		userAccount.remove(role);
	}

	// Customer
	public void setAddress(String street, String houseNumber, Number postCode, String city) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public Number getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}

	public void setPrivateInsurance(Boolean newInsurance) {
		this.privateInsurance = newInsurance;
	}

	// Employee
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

	public void takeVacation(Integer duration) {
		this.vacationRemaining -= duration;
	}
}
