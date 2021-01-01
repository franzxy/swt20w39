package pharmacy.user;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.springframework.data.util.Streamable;
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
	String user(Model model, UserForm userForm) {

		model.addAttribute("userForm", userForm);

		return "register";
	}

    @PostMapping("/register")
	String newUser(@Valid @ModelAttribute("userForm")UserForm userForm, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}

		userManagement.addUser(userForm);

		return "redirect:/login";
	}

	@GetMapping("/customers")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String customers(Model model) {

		model.addAttribute("users", userManagement.findAll());
		model.addAttribute("customer", Role.of("CUSTOMER"));

		return "customers";
	}

	@GetMapping("/customer/{userId}")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String customer(@PathVariable Long userId, Model model, AddressForm addressForm, EmployeeForm employeeForm) {
		
		var user = userManagement.findUser(userId).get();
		model.addAttribute("addressForm", addressForm);
		model.addAttribute("employeeForm", employeeForm);
		model.addAttribute("user", user);

		return "customer";
	}

	@PostMapping("/user/{userId}")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String changeUser(@PathVariable Long userId, Model model, @Valid @ModelAttribute("addressForm")AddressForm addressForm, Errors addressErrors, @Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors employeeErrors) {
		
		var user = userManagement.findUser(userId).get();
		model.addAttribute("user", user);
		model.addAttribute("userRole", user.getUserAccount().hasRole(Role.of("USER")));

		if (result.hasErrors()) {
			return "user";
		}
		
		userManagement.addEmployee(userManagement.findUser(userId).get(), employeeForm);

		return "user";
	}

	@GetMapping("/employees")
	@PreAuthorize("hasRole('BOSS')")
	String users(Model model) {

		model.addAttribute("users", userManagement.findAll());
		model.addAttribute("employee", Role.of("EMPLOYEE"));

		return "employees";
	}

	@GetMapping("/employee/{userId}")
	@PreAuthorize("hasRole('BOSS')")
	String employee(@PathVariable Long userId, Model model/*, EmployeeForm employeeForm*/) {
		
		var user = userManagement.findUser(userId).get();
		//model.addAttribute("employeeForm", employeeForm);
		model.addAttribute("user", user);

		return "employee";
	}
/*
	@PostMapping("/user/{userId}")
	@PreAuthorize("hasRole('BOSS') or hasRole('EMPLOYEE')")
	String changeUser(@Valid @ModelAttribute("employeeForm")EmployeeForm employeeForm, Errors result, @PathVariable Long userId, Model model) {
		
		var user = userManagement.findUser(userId).get();
		model.addAttribute("user", user);
		model.addAttribute("userRole", user.getUserAccount().hasRole(Role.of("USER")));

		if (result.hasErrors()) {
			return "user";
		}
		
		userManagement.addEmployee(userManagement.findUser(userId).get(), employeeForm);

		return "user";
	}
*/
	@GetMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changePassword(Model model, UserPasswordForm changePassword) {
		
		model.addAttribute("changePassword", changePassword);
		model.addAttribute("firstName", userManagement.currentUserName());
		
		return "account";
	}

	@PostMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changePassword(Model model, @Valid @ModelAttribute("changePassword") UserPasswordForm changePassword, Errors result) {
		
		model.addAttribute("firstName", userManagement.currentUserName());
		
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
