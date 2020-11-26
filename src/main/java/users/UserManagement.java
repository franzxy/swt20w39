import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@Transactional
public class UserManagement {

	public static final Role User_ROLE = Role.of("User");

	private final UserRepository Users;
	private final UserAccountManagement userAccounts;

	UserManagement(UserRepository Users, UserAccountManagement userAccounts) {
		Assert.notNull(users, "Users cannot be null.");
		Assert.notNull(userAccounts, "UserAccountManagement cannot be null.");

		this.users = users;
		this.userAccounts = userAccounts;
	}

	public User login(LoginForm form) {
		Assert.notNull(form, "Login form cannot be null.");

		var password = UnencryptedPassword.of(form.getPassword());
		var userAccount = userAccounts.create(form.getName(), password, User_ROLE);
		
		return users.save(new User(userAccount, form.getAddress()));
	}

	public Streamable<User> findAll() {
		return users.findAll();
	}
}
