package pharmacy.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

	@GetMapping("/search")
	String searchCatalog(@RequestParam(name="searchTerm", required=true) String searchTerm, Model model) {

		String[] search = searchTerm.split(" ");

		ArrayList<Medicine> result = new ArrayList<Medicine>();
		Iterator<Medicine> stock = catalog.findAll().iterator();

		while(stock.hasNext()) {
			Medicine d = stock.next();

			//Titelsuche
			for(int i = 0; i < search.length; i++) {
				if (d.getName().toLowerCase().contains(search[i].toLowerCase())) {
					if(!result.contains(d)) {
						result.add(d);
					}
				}
			}

			//Suche nach Tag
			for(int i = 0; i < search.length; i++) {
				if (d.getGenre().toLowerCase().contains(search[i].toLowerCase())) {
					if(!result.contains(d)) {
						result.add(d);
					}
				}
			}
		}

		model.addAttribute("catalog", result);
		model.addAttribute("title", "Ergebnisse für " searchTerm);

		return "catalog";


		//public String search(@RequestParam(value = "searchTerm", defaultValue = "") String searchTerm) {
		//	return "search";
		//}

	}
