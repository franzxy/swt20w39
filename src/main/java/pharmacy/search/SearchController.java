package pharmacy.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;

import java.util.ArrayList;
import java.util.Iterator;


@Controller

public class SearchController {

	@GetMapping("/searchform")
	public String searchForm(Model model) {
		model.addAttribute("searchform", new SearchForm());
		return "searchform";
	}

	@PostMapping("/searchform")
	public String submitSearchForm(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/search?searchTerm=" + form.getSearchTerm();

	}
}
