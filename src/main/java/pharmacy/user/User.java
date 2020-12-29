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
import org.salespointframework.payment.PaymentCard;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;

	@OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

	// Customer

	// private Address customerAddress;
	private PaymentMethod payment;
	private Boolean privateInsurance;

	// Employee

	// private Address employeeAddress;
	private Long iban;
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

	public void addAddress(Address newAddress) {
		addresses.add(newAddress);
	}

	// Customer
/*
	public Address getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(Address newCustomerAddress) {
		customerAddress = newCustomerAddress;
	}
*/
	public PaymentMethod getPayment() {
		return payment;
	}

	public void setPayment(PaymentMethod newPayment) {
		payment = newPayment;
	}

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}

	public void setPrivateInsurance(Boolean newInsurance) {
		privateInsurance = newInsurance;
	}

	// Employee
/*
	public Address getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(Address newEmployeeAddress) {
		employeeAddress = newEmployeeAddress;
	}
*/
	public Long getIban() {
		return iban;
	}

	public void setIban(Long newIban) {
		iban = newIban;
	}

	public Money getSalary() {
		return salary;
	}

	public void setSalary(Money newSalary) {
		salary = newSalary;
	}

	public Long getVacationRemaining() {
		return vacationRemaining;
	}

	public void setVacationRemaining(Long newVacationRemaining) {
		vacationRemaining = newVacationRemaining;
	}
/*
	public Collection<Vacation> getVacation() {
		return vacation;
	}

	public void setVacation(Vacation newVacation) {
		vacation.add(newVacation);
	}
*/
}
