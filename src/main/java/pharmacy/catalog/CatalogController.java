package pharmacy.catalog;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class CatalogController {
	
	

	private final MedicineCatalog catalog;
	//private final MultiInventory<MultiInventoryItem> inventory;
	//private final BusinessTime businessTime;

	CatalogController(MedicineCatalog medicineCatalog ){//MultiInventory<MultiInventoryItem> inventory,BusinessTime businessTime) {

		this.catalog = medicineCatalog;
		//this.inventory = inventory;
		//this.businessTime = businessTime;
	}


	@GetMapping("/")
	public String catalog(Model model) {
		model.addAttribute("searchform", new SearchForm());
		model.addAttribute("catalog", catalog.findAll());
		return "index";
	}


	@PostMapping("/")
	public String submitSearchInCatalog(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/search?searchTerm=" + form.getSearchTerm();
	}

	@GetMapping("/searchform")
	//TODO @PreAuthorize("hasRole('USER')")
	public String searchForm(Model model) {
		model.addAttribute("searchform", new SearchForm());
		return "searchform";
	}

	@PostMapping("/searchform")
	public String submitSearchForm(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		//p=rezeptpflichtig, m=typ, i=zutaten
		return "redirect:/search?searchTerm=" + form.getSearchTerm() + "&p=" + form.getNoPres() + "&m=" + form.getMedType();

	}

	@GetMapping("/search")
	public String searchCatalog(@RequestParam(name="searchTerm", required=true, defaultValue = "") String searchTerm, @RequestParam(name="p", defaultValue = "false") boolean nopres, @RequestParam(name="i", defaultValue = "all") String ingredient, @RequestParam(name="m", defaultValue = "all") String type, Model model) {

		String[] search = searchTerm.toLowerCase().split(" ");
		ArrayList<Medicine> result = new ArrayList<>();
		Iterator<Medicine> stock = catalog.findAll().iterator();

		while (stock.hasNext()) {
			Medicine d = stock.next();

			for (int i = 0; i < search.length; i++) {
				//Name des Medikaments enthält Suchbegriff
				if (searchTerm.equals("") || d.getName().toLowerCase().contains(search[i])) {

					//Rezeptpflichtigkeit egal oder Medikament rezeptfrei
					if(!nopres || !d.isPresonly()) {

						if(type.equals("all") || d.getDescription().contains(type) || d.getName().contains(type)) {


							if (!result.contains(d)) {
								result.add(d);
							}
						}

					}
				}
			}
		}


		model.addAttribute("catalog", result);

		if(result.size() == 0) model.addAttribute("Suchbegriff", "Keine Ergebnisse");
		else if(searchTerm.equals("")) model.addAttribute("Suchbegriff", "Alle Medikamente");
		else model.addAttribute("Suchbegriff", "Ergebnisse für \"" + searchTerm + "\":");

		return "searchresult";
	}



	// (｡◕‿◕｡)
	// Befindet sich die angesurfte Url in der Form /foo/5 statt /foo?bar=5 so muss man @PathVariable benutzen
	// Lektüre: http://spring.io/blog/2009/03/08/rest-in-spring-3-mvc/
	@GetMapping("/medicine/{medicine}")
	String detail(@PathVariable Medicine medicine, Model model) {



		/*var quantity = inventory.findByProductIdentifier(medicine.getId()) //
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);


		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
*/

		return "index";
	}


}