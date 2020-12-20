package pharmacy.user;

import org.salespointframework.useraccount.Password.UnencryptedPassword;

import java.sql.Time;
import java.time.Duration;
import java.time.Month;
import java.util.Optional;
import java.util.function.Predicate;

import org.javamoney.moneta.Money;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pharmacy.catalog.MedicineCatalog;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@Transactional
public class UserManagement {

	private final UserRepository users;
	private final UserAccountManagement userAccounts;

	UserManagement(UserRepository users, UserAccountManagement userAccounts) {
		
		this.users = users;
		this.userAccounts = userAccounts;
	}
/*
	public String getPassword() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName()).get();
		return UnencryptedPassword.of(user.getPassword().toString()).toString();
	}
*/
	public User addUser(UserForm userForm) {
		var password = UnencryptedPassword.of(userForm.getPassword());
		var userAccount = userAccounts.create(userForm.getEmail(), password, Role.of("USER"));
		userAccount.setFirstname(userForm.getName());
		userAccount.setLastname(userForm.getLastName());

		return users.save(new User(userAccount));
	}

	public String changePassword(UserPasswordForm form) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		userAccounts.changePassword(user.get(), UnencryptedPassword.of(form.getNewPassword()));
		
		return "password changed";
	}

	public User addCustomer(CustomerForm customerForm) {
		
		var password = UnencryptedPassword.of(customerForm.getPassword());
		var userAccount = userAccounts.create(customerForm.getEmail(), password, Role.of("CUSTOMER"));
		userAccount.setFirstname(customerForm.getName());
		userAccount.setLastname(customerForm.getLastName());
		var user = new User(userAccount);
		user.setAddress(customerForm.getStreet(), customerForm.getHouseNumber(), customerForm.getPostCode(), customerForm.getCity());
		user.setPrivateInsurance(customerForm.getPrivateInsurance());

		return users.save(user);
	}

	public User addDoctor(UserForm userForm) {
		
		var password = UnencryptedPassword.of(userForm.getPassword());
		var userAccount = userAccounts.create(userForm.getEmail(), password, Role.of("DOCTOR"));
		userAccount.setFirstname(userForm.getName());
		userAccount.setLastname(userForm.getLastName());
		var user = new User(userAccount);

		return users.save(user);
	}

	public User addEmployee(EmployeeForm employeeForm) {
		
		var password = UnencryptedPassword.of(employeeForm.getPassword());
		var userAccount = userAccounts.create(employeeForm.getEmail(), password, Role.of("EMPLOYEE"));
		userAccount.setFirstname(employeeForm.getName());
		userAccount.setLastname(employeeForm.getLastName());
		var user = new User(userAccount);
		user.setSalary(Money.of(employeeForm.getSalary(), "EUR"));
		user.setVacationRemaining(employeeForm.getVacation());

		return users.save(user);
	}

	public String addRole(User user, Role role) {
		
		user.addRole(role);

		return "role added";
	}

	public String removeRole(User user, Role role) {
		
		user.removeRole(role);

		return "role removed";
	}

	public String setCustomerInsurance(User user, Boolean newPrivateInsurance) {

		user.setPrivateInsurance(newPrivateInsurance);

		return "new insurance";
	}

	public String setEmployeeSalary(User user, Money newSalary) {

		user.setSalary(newSalary);

		return "new salary";
	}

	public Streamable<User> findAll() {
		return users.findAll();
	}

	public Optional<User> findUser(Long id) {
		return users.findById(id);
	}

	public String currentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		return user.get().getFirstname();
	}

	// public Boolean comparePasswords (one, two)
}
