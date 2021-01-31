package pharmacy.user;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Benutzer Controller
 * @author Timon Trettin
 */
@Controller
class UserController {

	private final UserManagement userManagement;

	/**
	 * Initialisiert den Controller.
	 * @param userManagement
	*/
	UserController(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

	/**
	 * Fügt notwendige Attribute zum Model hinzu bei Registrierung
	 * @param model
	 * @param userForm
	 * @return der Name der Ansicht
	 */
	@GetMapping("/register")
	String register(Model model, UserForm userForm) {

		model.addAttribute("userForm", userForm);

		return "register";
	}

	/**
	 * Nutzer wird registriert wenn keine Fehler
	 * @param userForm
	 * @param result
	 * @return der Name der Ansicht
	 */
    @PostMapping("/register")
	String changeRegister(@Valid @ModelAttribute("userForm")UserForm userForm, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}

		userManagement.addUser(userForm);

		return "redirect:/login";
	}

	/**
	 * Nutzer Liste
	 * @param model
	 * @param employeeForm
	 * @return der Name der Ansicht
	 */
	@GetMapping("/users")
	@PreAuthorize("hasRole('BOSS')")
	String users(Model model, EmployeeForm employeeForm) {

		model.addAttribute("employeeForm", employeeForm);

		model.addAttribute("users", userManagement.findAll());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "users";
	}

	/**
	 * Nutzer einstellen
	 * @param userId
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/user/{userId}/hire")
	@PreAuthorize("hasRole('BOSS')")
	String hireEmployee(@PathVariable Long userId) {
		
		userManagement.hireEmployee(userManagement.findUser(userId).get());

		return "redirect:/users";
	}

	/**
	 * Nutzer entlassen
	 * @param userId
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/user/{userId}/dismiss")
	@PreAuthorize("hasRole('BOSS')")
	String dismissEmployee(@PathVariable Long userId) {
		
		userManagement.dismissEmployee(userManagement.findUser(userId).get());

		return "redirect:/users";
	}

	/**
	 * Mitarbeiter gehalt einstellen
	 * @param userId
	 * @param employeeForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/user/{userId}/salary")
	@PreAuthorize("hasRole('BOSS')")
	String changeEmployeeSalary(@PathVariable Long userId, @Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors result) {

		if (result.hasErrors()) {
			return "redirect:/users";
		}
		
		userManagement.changeEmployee(userManagement.findUser(userId).get(), employeeForm);

		return "redirect:/users";
	}

	/**
	 * Nutzer entfernen
	 * @param userId
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/user/{userId}/remove")
	@PreAuthorize("hasRole('BOSS')")
	String removeUser(@PathVariable Long userId) {
		
		userManagement.removeUser(userManagement.findUser(userId).get());

		return "redirect:/users";
	}

	/**
	 * Urlaub genehmigen
	 * @param userId
	 * @param vacationId
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/user/{userId}/vacation/{vacationId}/approve")
	@PreAuthorize("hasRole('BOSS')")
	String approveVacations(@PathVariable Long userId, @PathVariable Integer vacationId) {
		
		userManagement.approveVacation(userManagement.findUser(userId).get(), vacationId);

		return "redirect:/users";
	}

	/**
	 * Urlaub ablehnen
	 * @param userId
	 * @param vacationId
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/user/{userId}/vacation/{vacationId}/remove")
	@PreAuthorize("hasRole('BOSS')")
	String rejectVacations(@PathVariable Long userId, @PathVariable Integer vacationId) {
		
		userManagement.removeVacation(userManagement.findUser(userId).get(), vacationId);

		return "redirect:/users";
	}

	/**
	 * Account Übersicht
	 * @param model
	 * @param pictureForm
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String account(Model model, PictureForm pictureForm) {
		
		model.addAttribute("pictureForm", pictureForm);

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "account";
	}

	/**
	 * Profilbild ändern falls keine Fehler
	 * @param model
	 * @param pictureForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/picture")
	@PreAuthorize("isAuthenticated()")
	String changePicture(Model model, @Valid @ModelAttribute("pictureForm")PictureForm pictureForm, Errors result) {
		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "settings";
		}
		
		userManagement.changePicture(userManagement.currentUser().get(), pictureForm);

		return "redirect:/account";
	}

	/**
	 * Passwort ändern
	 * @param model
	 * @param passwordForm
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/account/password")
	@PreAuthorize("isAuthenticated()")
	String password(Model model, PasswordForm passwordForm) {

		model.addAttribute("passwordForm", passwordForm);

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "password";
	}

	/**
	 * Versicherung ändern
	 * @param model
	 * @param insuranceForm
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/account/insurance")
	@PreAuthorize("isAuthenticated()")
	String insurance(Model model, InsuranceForm insuranceForm) {

		model.addAttribute("insuranceForm", insuranceForm);

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "insurance";
	}

	/**
	 * Adresse ändern
	 * @param model
	 * @param addressForm
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/account/address")
	@PreAuthorize("isAuthenticated()")
	String address(Model model, AddressForm addressForm) {

		model.addAttribute("addressForm", addressForm);

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "address";
	}

	/**
	 * Bezahlmethoden ändern
	 * @param model
	 * @param bankAccountForm
	 * @param creditCardForm
	 * @param payDirektForm
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/account/payments")
	@PreAuthorize("isAuthenticated()")
	String accountPayments(Model model, BankAccountForm bankAccountForm, PaymentCardForm creditCardForm, PayDirektForm payDirektForm) {

		model.addAttribute("bankAccountForm", bankAccountForm);
		model.addAttribute("creditCardForm", creditCardForm);
		model.addAttribute("payDirektForm", payDirektForm);

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "payments";
	}

	/**
	 * Bank Konto ändern falls keine Fehler
	 * @param model
	 * @param bankAccountForm
	 * @param creditCardForm
	 * @param payDirektForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/bankaccount")
	@PreAuthorize("isAuthenticated()")
	String changeAccountBank(Model model, @Valid @ModelAttribute("bankAccountForm")BankAccountForm bankAccountForm, Errors result, PaymentCardForm creditCardForm, PayDirektForm payDirektForm) {
		
		model.addAttribute("creditCardForm", creditCardForm);
		model.addAttribute("payDirektForm", payDirektForm);

		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "payments";
		}
		
		userManagement.changeBankAccount(bankAccountForm);

		return "redirect:/account/payments";
	}

	/**
	 * Kartenzahlung ändern falls keine Fehler
	 * @param model
	 * @param bankAccountForm
	 * @param creditCardForm
	 * @param payDirektForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/card")
	@PreAuthorize("isAuthenticated()")
	String changeAccountCard(Model model, @Valid @ModelAttribute("creditCardForm")PaymentCardForm creditCardForm, Errors result, BankAccountForm bankAccountForm, PayDirektForm payDirektForm) {
		
		model.addAttribute("bankAccountForm", bankAccountForm);
		model.addAttribute("payDirektForm", payDirektForm);

		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "payments";
		}
		
		userManagement.changePaymentCard(creditCardForm);

		return "redirect:/account/payments";
	}

	/**
	 * PayDirekt ändern falls keine Fehler
	 * @param model
	 * @param bankAccountForm
	 * @param creditCardForm
	 * @param payDirektForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/paydirekt")
	@PreAuthorize("isAuthenticated()")
	
	String changeAccountPay(Model model, @Valid @ModelAttribute("payDirektForm")PayDirektForm payDirektForm, Errors result, PaymentCardForm creditCardForm, BankAccountForm bankAccountForm) {
		
		model.addAttribute("bankAccountForm", bankAccountForm);
		model.addAttribute("creditCardForm", creditCardForm);

		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "payments";
		}
		
		userManagement.changePayDirekt(payDirektForm);

		return "redirect:/account/payments";
	}

	/**
	 * Passwort ändern falls keine Fehler
	 * @param model
	 * @param passwordForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/password")
	@PreAuthorize("isAuthenticated()")
	String changeAccountPassword(Model model, @Valid @ModelAttribute("passwordForm")PasswordForm passwordForm, Errors result) {
		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "password";
		}
		
		userManagement.changePassword(passwordForm);

		return "redirect:/account";
	}

	/**
	 * Versicherung ändern falls keine Fehler
	 * @param model
	 * @param insuranceForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/insurance")
	@PreAuthorize("isAuthenticated()")
	String changeAccountInsurance(Model model, @Valid @ModelAttribute("insuranceForm")InsuranceForm insuranceForm, Errors result) {
		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "insurance";
		}
		
		userManagement.changeInsurance(userManagement.currentUser().get(), insuranceForm);

		return "redirect:/account";
	}

	/**
	 * Adresse ändern falls keine Fehler
	 * @param model
	 * @param addressForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/account/address")
	@PreAuthorize("isAuthenticated()")
	String addAccountAddress(Model model, @Valid @ModelAttribute("addressForm")AddressForm addressForm, Errors result) {
		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "address";
		}
		
		userManagement.changeAddress(userManagement.currentUser().get(), addressForm);

		return "redirect:/account";
	}

	/**
	 * Account löschen
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/account/remove")	
	@PreAuthorize("hasRole('CUSTOMER')")
	String removeAccountUser() {
		
		userManagement.removeUser(userManagement.currentUser().get());

		return "redirect:/logout";
	}

	/**
	 * Admin Übersicht
	 * @param model
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('BOSS')")
	String admin(Model model) {

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "admin";
	}

	/**
	 * Mitarbeiter Übersicht
	 * @param model
	 * @param vacationForm
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	String vacation(Model model, VacationForm vacationForm) {

		model.addAttribute("vacationForm", vacationForm);

		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "employee";
	}

	/**
	 * Urlaub nehemn falls keine Fehler
	 * @param model
	 * @param vacationForm
	 * @param result
	 * @return der Name der Ansicht
	 */	
	@PostMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	String addVacation(Model model, @Valid @ModelAttribute("vacationForm")VacationForm vacationForm, Errors result) {
		
		model.addAttribute("user", userManagement.currentUser().get());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		if (result.hasErrors()) {
			return "employee";
		}
		
		userManagement.addVacation(userManagement.currentUser().get(), vacationForm);

		return "employee";
	}

	/**
	 * Urlaub löschen
	 * @param vacationId
	 * @return der Name der Ansicht
	 */	
	@GetMapping("/vacation/{vacationId}/remove")
	@PreAuthorize("hasRole('EMPLOYEE')")
	String removeVacation(@PathVariable Integer vacationId) {
		
		userManagement.removeVacation(userManagement.currentUser().get(), vacationId);

		return "redirect:/employee";
	}
}
