package pharmacy.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.ProductPaymentEntry;
import org.salespointframework.payment.CreditCard;
import org.salespointframework.payment.PaymentCard;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
/*
	@OneToMany(cascade = CascadeType.ALL)
	private List<PaymentMethod> payments = new ArrayList<>();
*/
	private String insurance;

	// Employee

	private Money salary;
/*
	@OneToMany(cascade = CascadeType.ALL)
	private List<Vacation> vacations = new ArrayList<>();

	private Long vacationRemaining;
*/
	@SuppressWarnings("unused")
	private User() {}

	public User(UserAccount userAccount, String name) {
		this.userAccount = userAccount;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void addRole(Role role) {
		userAccount.add(role);
	}

	public void removeRole(Role role) {
		userAccount.remove(role);
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void addAddress(Address newAddress) {
		addresses.add(newAddress);
	}
/*
	public List<PaymentMethod> getPayments() {
		return payments;
	}

	public void addPayment(PaymentMethod newPayment) {
		payments.add(newPayment);
	}
*/
	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String newInsurance) {
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
