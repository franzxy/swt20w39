package pharmacy.counters;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CounterController {

	@GetMapping("/counter")
	String counter() {
		return "counter";
	}
}
