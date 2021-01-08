package pharmacy.user;

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

	private Address address;
/*
	@OneToMany(cascade = CascadeType.ALL)
	private List<PaymentMethod> payments = new ArrayList<>();
*/
	private Insurance insurance;

	// Employee

	private Money salary;
/*
	@OneToMany(cascade = CascadeType.ALL)
	private List<Vacation> vacations = new ArrayList<>();

	private Long vacationRemaining;
*/
	@SuppressWarnings("unused")
	public User() {}

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

	public Address getAddress() {
		return address;
	}

	public void changeAddress(Address newAddress) {
		address = newAddress;
	}
/*
	public List<PaymentMethod> getPayments() {
		return payments;
	}

	public void addPayment(PaymentMethod newPayment) {
		payments.add(newPayment);
	}
*/
	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance newInsurance) {
		insurance = newInsurance;
	}

	// Employee

	public Money getSalary() {
		return salary;
	}

	public void setSalary(Money newSalary) {
		salary = newSalary;
	}
/*
	public Long getVacationRemaining() {
		return vacationRemaining;
	}

	public void setVacationRemaining(Long newVacationRemaining) {
		vacationRemaining = newVacationRemaining;
	}

	public List<Vacation> getVacations() {
		return vacations;
	}

	public void addVacation(Vacation newVacation) {
		vacations.add(newVacation);
	}
*/
}
