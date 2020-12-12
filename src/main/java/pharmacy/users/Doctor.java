package pharmacy.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Doctor extends User {

	public Doctor(UserAccount userAccount) {

		super(userAccount);
	}
}
