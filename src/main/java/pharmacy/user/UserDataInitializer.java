package pharmacy.user;

import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Order(10)
class UserDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(UserDataInitializer.class);

	private final UserAccountManagement userAccountManagement;
	private final UserManagement userManagement;
	private final UserRepository users;

	UserDataInitializer(UserAccountManagement userAccountManagement, UserManagement userManagement, UserRepository users) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		Assert.notNull(userManagement, "UserRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.userManagement = userManagement;
		this.users = users;
	}

	@Override
	public void initialize() {

		if (userAccountManagement.findByUsername("apostheke").isPresent()) {
			return;
		}

		LOG.info("Creating default users and users.");

		var boss = userAccountManagement.create("apostheke", UnencryptedPassword.of("#1234hans#"), Role.of("BOSS"));
		users.save(new User(boss));

		var emp = userAccountManagement.create("hansi", UnencryptedPassword.of("#1234hans#"), Role.of("EMPLOYEE"));
		users.save(new User(emp));
		
		List.of(
			new UserForm("hans peter", "#1234hans#", "#1234hans#"),
			new UserForm("hansi peter", "#1234hans#", "#1234hans#"),
			new UserForm("hansa peter", "#1234hans#", "#1234hans#"),
			new UserForm("hanso peter", "#1234hans#", "#1234hans#")
		).forEach(userManagement::addUser);
	}
}
