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
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
class UserController {

	private final UserManagement userManagement;

	UserController(UserManagement userManagement) {

		Assert.notNull(userManagement, "UserManagement must not be null!");

		this.userManagement = userManagement;
	}

	@PostMapping("/password")
	String changePassword(@Valid @ModelAttribute("form")PasswordForm form, Errors result) {
		if (result.hasErrors()) {
			return "password";
		}
		userManagement.changePassword(form);
		
		return "redirect:/";
	}

	@GetMapping("/password")
	String changePassword(Model model, PasswordForm form) {
		model.addAttribute("form", form);
		return "password";
	}

    @PostMapping("/register")
	String registerNew(@Valid @ModelAttribute("form")RegistrationForm form, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}
		
		userManagement.createUser(form);

		return "redirect:/";
	}

	@GetMapping("/register")
	String register(Model model, RegistrationForm form) {
		model.addAttribute("form", form);
		return "register";
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('BOSS')")
	String users(Model model) {

		model.addAttribute("userList", userManagement.findAll());

		return "users";
	}

	@GetMapping("/account")
	String account() {
		return "account";
	}
}
