package pharmacy.users.subclasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {

	private @Id @GeneratedValue long id;
	public enum Insurance {
		PUBLIC, PRIVATE
	};
	private Insurance insuranceType;
	private String address;
	private String salary;
	private String vacationRemaining;

	@OneToOne
	private UserAccount userAccount;

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

	public Insurance getInsuranceType() {
		return insuranceType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSalary() {
		return salary;
	}

	public String getVacationRemaining() {
		return vacationRemaining;
	}
}
