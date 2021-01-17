package pharmacy.user;

import java.util.List;

import org.javamoney.moneta.Money;
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

		if (userAccountManagement.findByUsername("apo").isPresent()) {
			return;
		}

		LOG.info("Creating default users and users.");

		var boss = userAccountManagement.create("apo", UnencryptedPassword.of("#123apo#"), Role.of("BOSS"));
		var bossUser = new User(boss);
		bossUser.setPicture("https://www.welt.de/img/regionales/hamburg/mobile169760744/3652503737-ci102l-w1300/Ahmad-Ahadi-Apored-vor-Gericht.jpg");
		users.save(bossUser);

		var emp = userAccountManagement.create("hans", UnencryptedPassword.of("#12hans#"), Role.of("EMPLOYEE"));
		var empUser = new User(emp);
		empUser.setSalary(Money.of(3000, "EUR"));
		users.save(empUser);
		
		List.of(
			new UserForm("hansi", "#12hans#", "#12hans#"),
			new UserForm("hanso", "#12hans#", "#12hans#"),
			new UserForm("hansa", "#12hans#", "#12hans#")
		).forEach(userManagement::addUser);
	}
}
