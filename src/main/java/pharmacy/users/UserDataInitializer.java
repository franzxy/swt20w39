package pharmacy.users;

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

	UserDataInitializer(UserAccountManagement userAccountManagement, UserManagement userManagement) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		Assert.notNull(userManagement, "UserRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.userManagement = userManagement;
	}

	@Override
	public void initialize() {

		if (userAccountManagement.findByUsername("boss").isPresent()) {
			return;
		}

		LOG.info("Creating default users and users.");

		userAccountManagement.create("boss", UnencryptedPassword.of("123"), Role.of("BOSS"));

		var password = "123";

		List.of(//
				new RegistrationForm("hans", password, "wurst"),
				new RegistrationForm("dextermorgan", password, "Miami-Dade County"),
				new RegistrationForm("earlhickey", password, "Camden County - Motel"),
				new RegistrationForm("mclovinfogell", password, "Los Angeles")//
		).forEach(userManagement::createUser);
	}
}
