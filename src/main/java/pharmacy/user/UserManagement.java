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

/**
 * Benutzer Management
 * @author Timon Trettin
 */
@Service
@Transactional
public class UserManagement {

	private final UserRepository users;
	private final UserAccountManagement userAccounts;

	/**
	 * Initialisiert das Nutzer Management.
	 * @param users
	 * @param userAccounts
	*/
	UserManagement(UserRepository users, UserAccountManagement userAccounts) {
		
		this.users = users;
		this.userAccounts = userAccounts;
	}
	
	/**
	 * Fügt Nutzer hinzu
	 * @param userForm
	 * @return User
	*/
	public User addUser(UserForm userForm) {
		var password = UnencryptedPassword.of(userForm.getPassword());
		var userAccount = userAccounts.create(userForm.getName(), password, Role.of("CUSTOMER"));

		return users.save(new User(userAccount));
	}
	
	/**
	 * Löscht Nutzer
	 * @param user
	 * @return Erfolgsnachricht
	*/
	public String removeUser(User user) {
		
		userAccounts.delete(user.getUserAccount());
		users.delete(user);

		return "user removed";
	}
	
	/**
	 * Überprüft altes Passwort
	 * @param old
	 * @return Ob Passwort übereinstimmt
	*/
	public Boolean checkPassword(String old) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(UnencryptedPassword.of(userAccounts.findByUsername(auth.getName()).get().getPassword().toString()).toString());
		return UnencryptedPassword.of(userAccounts.findByUsername(auth.getName()).get().getPassword().toString()).toString().equals(old);
	}
	
	/**
	 * Ändert Passwort
	 * @param form
	 * @return Erfolgsnachricht
	*/
	public String changePassword(PasswordForm form) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		var user = userAccounts.findByUsername(auth.getName());
		userAccounts.changePassword(user.get(), UnencryptedPassword.of(form.getNewPassword()));
		
		return "password changed";
	}
	
	/**
	 * Ändert Bank Konto
	 * @param form
	 * @return Erfolgsnachricht
	*/
	public String changeBankAccount(BankAccountForm form) {
		
		currentUser().get().setBankAccount(new BankAccount(form.getName(), form.getIban(), form.getBic()));
		
		return "bank account changed";
	}
	
	/**
	 * Ändert Kartenzahlung
	 * @param form
	 * @return Erfolgsnachricht
	*/
	public String changePaymentCard(PaymentCardForm form) {
		
		currentUser().get().setPaymentCard(new PaymentCard(form.getName(), form.getNumber(), form.getSecure()));
		
		return "credit card changed";
	}
	
	/**
	 * Ändert PayDirekt
	 * @param form
	 * @return Erfolgsnachricht
	*/
	public String changePayDirekt(PayDirektForm form) {
		
		currentUser().get().setPayDirekt(new PayDirekt(form.getName()));	
		
		return "pay direkt changed";
	}
	
	/**
	 * Ändert Versicherung
	 * @param user
	 * @param insuranceForm
	 * @return Erfolgsnachricht
	*/
	public String changeInsurance(User user, InsuranceForm insuranceForm) {
		
		user.setInsurance(new Insurance(insuranceForm.getCompany(), insuranceForm.getInsuranceNumber()));

		return "insurance changed";
	}
	
	/**
	 * Ändert Profilbild
	 * @param user
	 * @param pictureForm
	 * @return Erfolgsnachricht
	*/
	public String changePicture(User user, PictureForm pictureForm) {
		
		user.setPicture(pictureForm.getPicture());

		return "picture changed";
	}
	
	/**
	 * Ändert Adresse
	 * @param user
	 * @param addressForm
	 * @return Erfolgsnachricht
	*/
	public String changeAddress(User user, AddressForm addressForm) {
		
		user.changeAddress(new Address(addressForm.getName(), addressForm.getStreet(), addressForm.getPostCode(), addressForm.getCity()));

		return "address added";
	}
	
	/**
	 * Ändert Bestellangabe
	 * @param user
	 * @param newOrdered
	 * @return Erfolgsnachricht
	*/
	public String changeOrdered(User user, Boolean newOrdered) {
		
		user.setOrdered(newOrdered);

		return "ordered changed";
	}
	
	/**
	 * Stellt Mitarbeiter ein
	 * @param user
	 * @return Erfolgsnachricht
	*/
	public String hireEmployee(User user) {
		
		user.removeRole(Role.of("CUSTOMER"));
		user.addRole(Role.of("EMPLOYEE"));

		return "employee added";
	}
	
	/**
	 * Ändert Mitarbeiter Gehalt
	 * @param user
	 * @param employeeForm
	 * @return Erfolgsnachricht
	*/
	public String changeEmployee(User user, EmployeeForm employeeForm) {
		
		user.setSalary(Money.of(Long.valueOf(employeeForm.getSalary()), "EUR"));

		return "employee changed";
	}
	
	/**
	 * Entlässt Mitarbeiter ein
	 * @param user
	 * @return Erfolgsnachricht
	*/
	public String dismissEmployee(User user) {
		
		user.removeRole(Role.of("EMPLOYEE"));
		user.addRole(Role.of("CUSTOMER"));

		return "employee changed";
	}
	
	/**
	 * Fügt Nutzer Rolle hinzu
	 * @param user
	 * @param role
	 * @return Erfolgsnachricht
	*/
	public String addRole(User user, Role role) {
		
		user.addRole(role);

		return "role added";
	}
	
	/**
	 * Entfernt Nutzer Rolle
	 * @param user
	 * @param role
	 * @return Erfolgsnachricht
	*/
	public String removeRole(User user, Role role) {
		user.removeRole(role);

		return "role removed";
	}
	
	/**
	 * Ändert Mitarbeiter Gehalt
	 * @param user
	 * @param newSalary
	 * @return Erfolgsnachricht
	*/
	public String setEmployeeSalary(User user, Money newSalary) {

		user.setSalary(newSalary);

		return "new salary";
	}
	
	/**
	 * Fügt Urlaub hinzu
	 * @param user
	 * @param vacationForm
	 * @return Erfolgsnachricht
	*/
	public String addVacation(User user, VacationForm vacationForm) {
		user.addVacation(new Vacation(vacationForm.getStartDate(), vacationForm.getEndDate()));
		return "vacation added";
	}
	
	/**
	 * Genehmigt Urlaub
	 * @param user
	 * @param index
	 * @return Erfolgsnachricht
	*/
	public String approveVacation(User user, Integer index) {
		var vac = user.getVacations().get(index);
		if (vac.getDuration() < user.getVacationRemaining()) {
			vac.setApproved(true);
			user.setVacationRemaining(user.getVacationRemaining() - vac.getDuration().intValue());
		}
		
		return "vacation added";
	}
	
	/**
	 * Entfernt Urlaub
	 * @param user
	 * @param index
	 * @return Erfolgsnachricht
	*/
	public String removeVacation(User user, Integer index) {
		user.removeVacation(index);
		return "vacation added";
	}
	
	/**
	 * Gibt alle Nutzer aus
	 * @return Nutzerliste
	*/
	public Streamable<User> findAll() {
		return users.findAll();
	}
	
	/**
	 * Gibt angemeldeten Nutzer aus
	 * @return Angemeldeter Nutzer
	*/
	public Optional<User> currentUser() {
		return users.findAll().get().filter(u -> u.getUserAccount().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())).findFirst();
	}
	
	/**
	 * Gibt alle Nutzer aus
	 * @param id
	 * @return Nutzer
	*/
	public Optional<User> findUser(Long id) {
		return users.findById(id);
	}
}
