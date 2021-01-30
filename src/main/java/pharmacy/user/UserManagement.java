package pharmacy.user;

import org.salespointframework.useraccount.Password.EncryptedPassword;
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

	public String removeUser(User user) {
		
		userAccounts.delete(user.getUserAccount());
		users.delete(user);

		return "user removed";
	}

	public Boolean checkPassword(String old) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(UnencryptedPassword.of(userAccounts.findByUsername(auth.getName()).get().getPassword().toString()).toString());
		return UnencryptedPassword.of(userAccounts.findByUsername(auth.getName()).get().getPassword().toString()).toString().equals(old);
	}

	public String changePassword(PasswordForm form) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		userAccounts.changePassword(user.get(), UnencryptedPassword.of(form.getNewPassword()));
		
		return "password changed";
	}

	public String changeBankAccount(BankAccountForm form) {
		
		currentUser().get().setBankAccount(new BankAccount(form.getName(), form.getIban(), form.getBic()));
		
		return "bank account changed";
	}

	public String changePaymentCard(PaymentCardForm form) {
		
		currentUser().get().setPaymentCard(new PaymentCard(form.getName(), form.getNumber(), form.getSecure()));
		
		return "credit card changed";
	}

	public String changePayDirekt(PayDirektForm form) {
		
		currentUser().get().setPayDirekt(new PayDirekt(form.getName()));	
		
		return "pay direkt changed";
	}
	
	public String changeInsurance(User user, InsuranceForm insuranceForm) {
		
		user.setInsurance(new Insurance(insuranceForm.getCompany(), insuranceForm.getInsuranceNumber()));

		return "insurance changed";
	}
	
	public String changePicture(User user, PictureForm pictureForm) {
		
		user.setPicture(pictureForm.getPicture());

		return "picture changed";
	}
	
	public String changeAddress(User user, AddressForm addressForm) {
		
		user.changeAddress(new Address(addressForm.getName(), addressForm.getStreet(), addressForm.getPostCode(), addressForm.getCity()));

		return "address added";
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
	public String hireEmployee(User user) {
		
		user.removeRole(Role.of("CUSTOMER"));
		user.addRole(Role.of("EMPLOYEE"));

		return "employee added";
	}
	
	public String changeOrdered(User user, Boolean newOrdered) {
		
		user.setOrdered(newOrdered);

		return "ordered changed";
	}
	
	public String changeEmployee(User user, EmployeeForm employeeForm) {
		
		user.setSalary(Money.of(Long.valueOf(employeeForm.getSalary()), "EUR"));

		return "employee changed";
	}
	
	public String dismissEmployee(User user) {
		
		user.removeRole(Role.of("EMPLOYEE"));
		user.addRole(Role.of("CUSTOMER"));

		return "employee changed";
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

	public String setEmployeeSalary(User user, Money newSalary) {

		user.setSalary(newSalary);

		return "new salary";
	}

	public String addVacation(User user, VacationForm vacationForm) {
		user.addVacation(new Vacation(vacationForm.getStartDate(), vacationForm.getEndDate()));
		return "vacation added";
	}

	public String approveVacation(User user, Integer index) {
		var vac = user.getVacations().get(index);
		if (vac.getDuration() < user.getVacationRemaining()) {
			vac.setApproved(true);
			user.setVacationRemaining(user.getVacationRemaining() - vac.getDuration().intValue());
		}
		
		return "vacation added";
	}

	public String removeVacation(User user, Integer index) {
		user.removeVacation(index);
		return "vacation added";
	}

	public Streamable<User> findAll() {
		return users.findAll();
	}

	public Optional<User> currentUser() {
		return users.findAll().get().filter(u -> u.getUserAccount().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())).findFirst();
	}

	public Optional<User> findUser(Long id) {
		return users.findById(id);
	}
}
