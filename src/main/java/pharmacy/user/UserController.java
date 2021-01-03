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
	String users(Model model) {

		model.addAttribute("users", userManagement.findAll());
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "users";
	}

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('BOSS')")
	String user(@PathVariable Long userId, Model model, EmployeeForm employeeForm) {
		
		model.addAttribute("employeeForm", employeeForm);

		var user = userManagement.findUser(userId).get();
		model.addAttribute("user", user);

		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		return "user";
	}

	@PostMapping("/user/{userId}")
	@PreAuthorize("hasRole('BOSS')")
	String changeUser(@PathVariable Long userId, Model model, @Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors result) {
		
		var user = userManagement.findUser(userId).get();
		model.addAttribute("user", user);
		
		model.addAttribute("customer", Role.of("CUSTOMER"));
		model.addAttribute("employee", Role.of("EMPLOYEE"));
		model.addAttribute("boss", Role.of("BOSS"));

		if (result.hasErrors()) {
			return "user";
		}
		
		userManagement.addEmployee(userManagement.findUser(userId).get(), employeeForm);

		return "user";
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
