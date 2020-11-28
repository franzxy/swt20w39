package buchhaltung;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuchhaltungController {
		
	public BuchhaltungController() {
		
	}
	
	@GetMapping("/buchhaltung")
	@PreAuthorize("hasRole('BOSS')")
	String buchhaltung() {
		return "buchhaltung";
	}
}
