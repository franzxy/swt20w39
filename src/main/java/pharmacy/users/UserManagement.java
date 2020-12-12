package pharmacy.users;

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

	private final UserRepository users;
	private final UserAccountManagement userAccounts;

	UserManagement(UserRepository users, UserAccountManagement userAccounts) {
		
		this.users = users;
		this.userAccounts = userAccounts;
	}

	public User addUser(UserForm userForm) {

		var password = UnencryptedPassword.of(userForm.getPassword());
		var userAccount = userAccounts.create(userForm.getEmail(), password, "USER");
		userAccount.setFirstname(userForm.getName());

		return users.save(new User(userAccount));
	}

	public User addCustomer(CustomerForm customerForm) {

		return 
			customerForm.getStreet(), 
			customerForm.getHouseNumber(), 
			customerForm.getPostCode(), 
			customerForm.getCity(), 
			customerForm.getPrivateInsurance()
		));
	}

	public User addDoctor(UserRegistrationForm userRegistrationForm) {

		var password = UnencryptedPassword.of(userRegistrationForm.getPassword());
		var userAccount = userAccounts.create(userRegistrationForm.getEmail(), password, "DOCTOR");
		userAccount.setFirstname(userRegistrationForm.getName());

		return doctors.save(new Doctor(userAccount));
	}

	public User addEmployee(UserRegistrationForm userRegistrationForm, EmployeeAddForm employeeAddForm) {

		var password = UnencryptedPassword.of(userRegistrationForm.getPassword());
		var userAccount = userAccounts.create(userRegistrationForm.getEmail(), password, "EMPLOYEE");
		userAccount.setFirstname(userRegistrationForm.getName());

		return empolyees.save(new Employee(userAccount, employeeAddForm.getSalary(), employeeAddForm.getVacation()));
	}

	public String changePassword(UserPasswordForm form) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		userAccounts.changePassword(user.get(), UnencryptedPassword.of(form.getNewPassword()));
		
		return "password changed";
	}

	public Streamable<User> findAll() {
		return users.findAll();
	}

	public String currentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		return user.get().getFirstname();
	}
}
