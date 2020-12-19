package pharmacy.user;

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
	String customer(Model model, CustomerForm customerForm) {

		model.addAttribute("customerForm", customerForm);
		
		return "customer";
	}

    @PostMapping("/customer")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String newCustomer(@Valid @ModelAttribute("customerForm")CustomerForm customerForm, Errors result) {

		if (result.hasErrors()) {
			return "customer";
		}

		userManagement.addCustomer(customerForm);

		return "redirect:/users";
	}

	@GetMapping("/doctor")
	@PreAuthorize("hasRole('BOSS')")
	String doctor(Model model, UserForm userForm) {

		model.addAttribute("userForm", userForm);

		return "doctor";
	}

    @PostMapping("/doctor")
	@PreAuthorize("hasRole('BOSS')")
	String newDoctor(@Valid @ModelAttribute("userForm")UserForm userForm, Errors result) {

		if (result.hasErrors()) {
			return "doctor";
		}

		userManagement.addDoctor(userForm);

		return "redirect:/users";
	}

	@GetMapping("/employee")
	@PreAuthorize("hasRole('BOSS')")
	String employee(Model model, EmployeeForm employeeForm) {
		
		model.addAttribute("employeeForm", employeeForm);
		
		return "employee";
	}

    @PostMapping("/employee")
	@PreAuthorize("hasRole('BOSS')")
	String newEmployee(@Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors result) {

		if (result.hasErrors()) {
			return "employee";
		}

		userManagement.addEmployee(employeeForm);

		return "redirect:/users";
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
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
