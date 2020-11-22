import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class User {

	private @Id @GeneratedValue long id;

	private String address;

	@OneToOne
	private UserAccount userAccount;

	@SuppressWarnings("unused")
	private User() {}

	public User(UserAccount userAccount, String address) {
		this.userAccount = userAccount;
        this.address = address;
	}

	public long getId() {
		return id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}
