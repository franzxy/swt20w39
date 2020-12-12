package pharmacy.users;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class UserController {

	private final UserManagement userManagement;

	UserController(UserManagement userManagement) {

		Assert.notNull(userManagement, "UserManagement must not be null!");

		this.userManagement = userManagement;
	}

	@GetMapping("/customer")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String manageNewCustomer(
		Model model, 
		CustomerRegistrationForm customerRegistrationForm
	) {
		model.addAttribute("customerRegistrationForm", customerRegistrationForm);
		return "customer";
	}

    @PostMapping("/customer")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String newCustomer(
		@Valid @ModelAttribute("customerRegistrationForm")CustomerRegistrationForm customerRegistrationForm, 
		Errors result
	) {

		if (result.hasErrors()) {
			return "customer";
		}
		userManagement.addCustomer(customerRegistrationForm);

		return "redirect:/users";
	}

	@GetMapping("/doctor")
	@PreAuthorize("hasRole('BOSS')")
	String doctor(Model model, UserRegistrationForm userRegistrationForm) {
		model.addAttribute("userRegistrationForm", userRegistrationForm);
		return "doctor";
	}

    @PostMapping("/doctor")
	@PreAuthorize("hasRole('BOSS')")
	String newDoctor(
		@Valid @ModelAttribute("userRegistrationForm")UserRegistrationForm userRegistrationForm, 
		Errors result
	) {

		if (result.hasErrors()) {
			return "customer";
		}
		userManagement.addDoctor(userRegistrationForm);

		return "redirect:/users";
	}

	@GetMapping("/employee")
	@PreAuthorize("hasRole('BOSS')")
	String employee(
		Model model, 
		UserRegistrationForm userRegistrationForm, 
		EmployeeAddForm employeeAddForm
	) {
		model.addAttribute("userRegistrationForm", userRegistrationForm);
		model.addAttribute("employeeAddForm", employeeAddForm);
		return "employee";
	}

    @PostMapping("/employee")
	@PreAuthorize("hasRole('BOSS')")
	String newEmployee(
		@Valid @ModelAttribute("userRegistrationForm")UserRegistrationForm userRegistrationForm, 
		@Valid @ModelAttribute("employeeAddForm")EmployeeAddForm employeeAddForm,
		Errors result
	) {

		if (result.hasErrors()) {
			return "customer";
		}
		userManagement.addEmployee(userRegistrationForm, employeeAddForm);

		return "redirect:/users";
	}

	@GetMapping("/customers")
	@PreAuthorize("hasRole('Employee')")
	String customers(Model model) {

		model.addAttribute("userList", userManagement.findAll());

		return "customer";
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('BOSS')")
	String users(Model model) {

		model.addAttribute("userList", userManagement.findAll());

		return "users";
	}

	@GetMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changePassword(Model model, UserPasswordForm changePassword) {
		model.addAttribute("changePassword", changePassword);
		model.addAttribute("userName", userManagement.currentUserName());
		return "account";
	}

	@PostMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changePassword(Model model, @Valid @ModelAttribute("changePassword") UserPasswordForm changePassword, Errors result) {
		model.addAttribute("userName", userManagement.currentUserName());
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
