package pharmacy.catalog;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.MultiInventory;
import org.salespointframework.inventory.MultiInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

@Controller
class CatalogController {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	private final MultiInventory<MultiInventoryItem> inventory;
	private final BusinessTime businessTime;

	CatalogController(MedicineCatalog medicineCatalog, MultiInventory<MultiInventoryItem> inventory, BusinessTime businessTime) {
		this.catalog = medicineCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
	}

	@GetMapping("/")
	public String catalog(Model model) {

		Iterator<Medicine> stock = catalog.findAll().iterator();
		ArrayList<Medicine> result = new ArrayList<>();

		while(stock.hasNext()) {
			Medicine d = stock.next();

			if(d.getIngType().toString().toLowerCase().equals("shop")) {
				result.add(d);
			}
		}

		model.addAttribute("catalog", result);
		model.addAttribute("searchform", new SearchForm());
		return "index";
	}


	@PostMapping("/")
	public String submitSearchInCatalog(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/search?searchTerm=" + form.getSearchTerm()  + "&i=shop";
	}


	@GetMapping("/searchform")
	public String searchForm(Model model) {
		model.addAttribute("searchform", new SearchForm());
		return "searchform";
	}

	@PostMapping("/searchform")
	public String submitSearchForm(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/search?searchTerm=" + form.getSearchTerm() + "&p=" + form.getNoPres() + "&m=" + form.getMedType() + "&i=shop";
	}

	@GetMapping("/search")
	//p=rezeptpflichtig, m=typ, i=zutaten
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
					if(!nopres || d.getPresType().equals("Frei Verkäuflich")) {

						if(type.equals("all") || d.getMedType().equals(type)) {

							if(ingredient.equals("all") || d.getIngType().toString().toLowerCase().equals(ingredient)) {
								if (!result.contains(d)) {
									result.add(d);
								}
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

	@GetMapping("/medicine/{medicine}")
	public String detail(@PathVariable Medicine medicine, Model model) {

		var quantity = inventory.findByProductIdentifier(medicine.getId()).map(InventoryItem::getQuantity);
		//var quantity = inventory.findByProductIdentifier(medicine.getId()).map(InventoryItem::getQuantity).orElse(NONE);

		model.addAttribute("medicine" , medicine);
		model.addAttribute("quantity", i.toString());
		//model.addAttribute("orderable", quantity.isGreaterThan(NONE));

		return "detail";
	}
}