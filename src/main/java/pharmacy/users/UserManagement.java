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
	private final CustomerRepository customers;
	private final DoctorRepository doctors;
	private final EmployeeRepository empolyees;
	private final UserAccountManagement userAccounts;

	UserManagement(

		UserRepository users, 
		CustomerRepository customers, 
		DoctorRepository doctors, 
		EmployeeRepository empolyees,
		UserAccountManagement userAccounts		
	) {

		this.users = users;
		this.customers = customers;
		this.doctors = doctors;
		this.empolyees = empolyees;
		this.userAccounts = userAccounts;
	}

	public User addUser(UserRegistrationForm userRegistrationForm) {

		var password = UnencryptedPassword.of(userRegistrationForm.getPassword());
		var userAccount = userAccounts.create(userRegistrationForm.getEmail(), password, "USER");
		userAccount.setFirstname(userRegistrationForm.getName());

		return users.save(new User(userAccount));
	}

	public User addCustomer(CustomerRegistrationForm customerRegistrationForm) {

		var password = UnencryptedPassword.of(customerRegistrationForm.getPassword());
		var userAccount = userAccounts.create(customerRegistrationForm.getEmail(), password, "CUSTOMER");
		userAccount.setFirstname(customerRegistrationForm.getName());

		return customers.save(new Customer(

			userAccount, 
			customerRegistrationForm.getStreet(), 
			customerRegistrationForm.getHouseNumber(), 
			customerRegistrationForm.getPostCode(), 
			customerRegistrationForm.getCity(), 
			customerRegistrationForm.getPrivateInsurance()
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
