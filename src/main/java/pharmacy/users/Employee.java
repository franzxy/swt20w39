package pharmacy.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Employee extends User {

	private Money salary;
	private Integer vacation;
	private Integer vacationRemaining;

	public Employee(UserAccount userAccount, Money salary, Integer vacation) {

		super(userAccount);
		this.salary = salary;
		this.vacation = vacation;
		this.vacationRemaining = vacation;
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
