package pharmacy.user;

import javax.mail.Address;
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

@Controller
class UserController {

	private final UserManagement userManagement;

	UserController(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

	@GetMapping("/register")
	String register(Model model, UserForm userForm) {

		model.addAttribute("userForm", userForm);

		return "register";
	}

    @PostMapping("/register")
	String changeRegister(@Valid @ModelAttribute("userForm")UserForm userForm, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}

		userManagement.addUser(userForm);

		return "redirect:/login";
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('BOSS')")
	String users(Model model, EmployeeForm employeeForm, InsuranceForm insuranceForm, AddressForm addressForm) {

		model.addAttribute("insuranceForm", insuranceForm);
		model.addAttribute("addressForm", addressForm);
		model.addAttribute("employeeForm", employeeForm);

		model.addAttribute("users", userManagement.findAll());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "users";
	}
	
	@PostMapping("/user/{userId}/insurance")
	@PreAuthorize("hasRole('BOSS')")
	String changeInsurance(@PathVariable Long userId, @Valid @ModelAttribute("insuranceForm")InsuranceForm insuranceForm, Errors result) {
		
		if (result.hasErrors()) {
			return "redirect:/users";
		}
		
		userManagement.changeInsurance(userManagement.findUser(userId).get(), insuranceForm);

		return "redirect:/users";
	}
	
	@PostMapping("/user/{userId}/address")
	@PreAuthorize("hasRole('BOSS')")
	String addAddress(@PathVariable Long userId, @Valid @ModelAttribute("addressForm")AddressForm addressForm, Errors result) {
		
		if (result.hasErrors()) {
			return "redirect:/users";
		}
		
		userManagement.changeAddress(userManagement.findUser(userId).get(), addressForm);

		return "redirect:/users";
	}
	
	@PostMapping("/user/{userId}/hire")
	@PreAuthorize("hasRole('BOSS')")
	String hireEmployee(@PathVariable Long userId, @Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors result) {
		
		if (result.hasErrors()) {
			return "redirect:/users";
		}
		
		userManagement.hireEmployee(userManagement.findUser(userId).get(), employeeForm);

		return "redirect:/users";
	}
	
	@GetMapping("/user/{userId}/dismiss")
	@PreAuthorize("hasRole('BOSS')")
	String dismissEmployee(@PathVariable Long userId) {
		
		userManagement.dismissEmployee(userManagement.findUser(userId).get());

		return "redirect:/users";
	}

	@PostMapping("/user/{userId}/salary")
	@PreAuthorize("hasRole('BOSS')")
	String changeEmployeeSalary(@PathVariable Long userId, @Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors result) {

		if (result.hasErrors()) {
			return "redirect:/users";
		}
		
		userManagement.changeEmployee(userManagement.findUser(userId).get(), employeeForm);

		return "redirect:/users";
	}

	@GetMapping("/user/{userId}/remove")
	@PreAuthorize("hasRole('BOSS')")
	String removeUser(@PathVariable Long userId) {
		
		userManagement.removeUser(userManagement.findUser(userId).get());

		return "redirect:/users";
	}

	@GetMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String account(Model model, UserPasswordForm changePassword) {

		model.addAttribute("changePassword", changePassword);
		
		return "account";
	}

	@PostMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changeAccount(Model model, @Valid @ModelAttribute("changePassword") UserPasswordForm changePassword, Errors result) {
				
		if (result.hasErrors()) {
			return "account";
		}
		
		userManagement.changePassword(changePassword);
		
		return "account";
	}
/*
	String example(@LoggedIn Optional<UserAccount> userAccount) {
        // functional style using map and lambda expression:
        return userAccount.map(account -> {
            // things to be done if the user account is present
            return "...";
        }).orElse("redirect:/");  // if the user account is *not* present
	}
*/
}
