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

	@GetMapping("/register")
	String register(Model model, RegistrationForm form) {

		model.addAttribute("form", form);
		
		return "register";
	}

    @PostMapping("/register")
	String registerNew(@Valid @ModelAttribute("form")RegistrationForm form, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}
		
		userManagement.createUser(form);

		return "redirect:/";
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('BOSS')")
	String users(Model model) {

		model.addAttribute("userList", userManagement.findAll());

		return "users";
	}

	@GetMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changePassword(Model model, PasswordForm form) {
		model.addAttribute("changePassword", form);
		model.addAttribute("userName", userManagement.currentUserName());
		return "account";
	}

	@PostMapping("/account")
	@PreAuthorize("isAuthenticated()")
	String changePassword(Model model, @Valid @ModelAttribute("changePassword") PasswordForm form, Errors result) {
		model.addAttribute("userName", userManagement.currentUserName());
		if (result.hasErrors()) {
			return "account";
		}
		userManagement.changePassword(form);
		return "account";
	}
}
