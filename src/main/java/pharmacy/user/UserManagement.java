package pharmacy.user;

import org.salespointframework.useraccount.Password.UnencryptedPassword;

import java.util.Optional;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		var userAccount = userAccounts.create(userForm.getName(), password, Role.of("CUSTOMER"));

		return users.save(new User(userAccount));
	}

	public String changePassword(UserPasswordForm form) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		userAccounts.changePassword(user.get(), UnencryptedPassword.of(form.getNewPassword()));
		
		return "password changed";
	}
/*
	public String addCustomer(User user, CustomerForm customerForm) {
		
		var password = UnencryptedPassword.of(customerForm.getPassword());
		var userAccount = userAccounts.create(customerForm.getEmail(), password, Role.of("CUSTOMER"));
		userAccount.setFirstname(customerForm.getName());
		userAccount.setLastname(customerForm.getLastName());
		var user = new User(userAccount);
		user.setAddress(customerForm.getStreet(), customerForm.getHouseNumber(), customerForm.getPostCode(), customerForm.getCity());
		user.setPrivateInsurance(customerForm.getPrivateInsurance());

		return "customer added";
	}

	public String changeCustomer(User user, CustomerDetailForm customerDetailForm) {
		
		var password = UnencryptedPassword.of(customerForm.getPassword());
		var userAccount = userAccounts.create(customerForm.getEmail(), password, Role.of("CUSTOMER"));
		userAccount.setFirstname(customerForm.getName());
		userAccount.setLastname(customerForm.getLastName());
		var user = new User(userAccount);
		user.setAddress(customerForm.getStreet(), customerForm.getHouseNumber(), customerForm.getPostCode(), customerForm.getCity());
		user.setPrivateInsurance(customerForm.getPrivateInsurance());

		return "customer changed";
	}
*/
	public String addEmployee(User user) {
		
		
		return "employee added";
	}
/*
	public String changeEmployee(User user, EmployeeDetailForm employeeDetailForm) {
		
		var password = UnencryptedPassword.of(customerForm.getPassword());
		var userAccount = userAccounts.create(customerForm.getEmail(), password, Role.of("CUSTOMER"));
		userAccount.setFirstname(customerForm.getName());
		userAccount.setLastname(customerForm.getLastName());
		var user = new User(userAccount);
		user.setAddress(customerForm.getStreet(), customerForm.getHouseNumber(), customerForm.getPostCode(), customerForm.getCity());
		user.setPrivateInsurance(customerForm.getPrivateInsurance());

		return "employee changed";
	}
*/
	public String addRole(User user, Role role) {
		
		user.addRole(role);

		return "role added";
	}

	public String removeRole(User user, Role role) {
		
		user.removeRole(role);

		return "role removed";
	}

	public String setInsurance(User user, String newInsurance) {

		user.setInsurance(newInsurance);

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
}
