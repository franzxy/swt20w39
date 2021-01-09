package pharmacy.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.scheduling.annotation.Scheduled;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;

	private Address address;

	private Insurance insurance;
	private String payDirekt;

	private String iban;
	private Money salary;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Vacation> vacations = new ArrayList<>();

	private Integer vacationRemaining;
	
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

	public String getIban() {
		return iban;
	}

	public void setIban(String newIban) {
		iban = newIban;
	}

	public String getPayDirekt() {
		return payDirekt;
	}

	public void setPayDirekt(String newPayDirekt) {
		payDirekt = newPayDirekt;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance newInsurance) {
		insurance = newInsurance;
	}

	public Money getSalary() {
		return salary;
	}

	public void setSalary(Money newSalary) {
		salary = newSalary;
	}

	@Scheduled(cron = "@yearly")
	protected void setVacationRemaining(){
		
		vacationRemaining = 30;
	}

	public Integer getVacationRemaining() {
		return vacationRemaining;
	}

	public void setVacationRemaining(Integer newVacationRemaining) {
		vacationRemaining = newVacationRemaining;
	}

	public List<Vacation> getVacations() {
		return vacations;
	}

	public void addVacation(Vacation newVacation) {
		vacations.add(newVacation);
	}

	public void removeVacation(Integer vac) {
		vacations.remove(vacations.get(vac));
	}

}
