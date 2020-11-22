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
		Assert.notNull(UserManagement, "There must be a UserManagement.");
		this.userManagement = userManagement;
	}

	@PostMapping("/login")
	String login(@Valid @ModelAttribute("form")Login form, Errors result) {
		if (result.hasErrors()) {
			return "login";
		}
		userManagement.login(form);
		
		return "redirect:/";
	}

	@GetMapping("/login")
	String changePassword(Model model, LoginForm form) {
		model.addAttribute("form", form);
		return "login";
	}
}
