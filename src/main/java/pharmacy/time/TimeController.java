package pharmacy.time;

import java.time.Duration;

import javax.validation.Valid;

import org.salespointframework.time.BusinessTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class TimeController {

	private final BusinessTime businessTime;

	TimeController(BusinessTime businessTime) {
		this.businessTime = businessTime;
	}

	@GetMapping("/time")
	@PreAuthorize("hasRole('BOSS')")
	String time(Model model, DurationForm durationForm) {

		model.addAttribute("time", businessTime.getTime());
		model.addAttribute("durationForm", durationForm);
		
		return "time";
	}

    @PostMapping("/time")
	@PreAuthorize("hasRole('BOSS')")
	String changeTime(Model model, @Valid @ModelAttribute("durationForm")DurationForm durationForm, Errors result) {

		model.addAttribute("time", businessTime.getTime());

		if (result.hasErrors()) {
			return "time";
		}

		businessTime.forward(Duration.ofHours(durationForm.getDuration()));

		return "redirect:/time";
	}
}
