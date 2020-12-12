package pharmacy.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	@OneToOne
	private UserAccount userAccount;

	public User(UserAccount userAccount) {
		
		this.userAccount = userAccount;
	}

	public void addRole(Role role) {
		userAccount.add(role);
	}

	public void removeRole(Role role) {
		userAccount.remove(role);
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}
}
