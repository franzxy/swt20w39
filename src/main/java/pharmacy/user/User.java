package pharmacy.user;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;

	// private CustomerAddress address;

	// Customer idea
	private String street;
	private String houseNumber;
	private Long postCode;
	private String city;
	private Boolean privateInsurance;


	// Employee
	private Money salary;
	// private Collection<Vacation> vacation;
	private Long vacationRemaining;

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
/*
	public CustomerAddress getAddress() {
		return address;
	}

	public void setAddress(CustomerAddress newAddress) {
		address = newAddress;
	}
*/

	// Customer idea
	public void setAddress(String street, String houseNumber, Long postCode, String city) {
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

	public Long getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}

	public void setPrivateInsurance(Boolean newInsurance) {
		privateInsurance = newInsurance;
	}

	// Employee
	public Money getSalary() {
		return salary;
	}

	public void setSalary(Money newSalary) {
		salary = newSalary;
	}
/*
	public Collection<Vacation> getVacation() {
		return vacation;
	}

	public void setVacation(Vacation newVacation) {
		vacation.add(newVacation);
	}
*/
	public Long getVacationRemaining() {
		return vacationRemaining;
	}

	public void setVacationRemaining(Long newVacationRemaining) {
		vacationRemaining = newVacationRemaining;
	}
}
