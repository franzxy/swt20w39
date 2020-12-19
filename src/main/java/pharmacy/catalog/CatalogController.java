package pharmacy.catalog;

import org.javamoney.moneta.Money;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.salespointframework.core.Currencies.EURO;

@Controller
class CatalogController {

	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final BusinessTime businessTime;

	CatalogController(MedicineCatalog medicineCatalog, UniqueInventory<UniqueInventoryItem> inventory,
			BusinessTime businessTime) {

		this.catalog = medicineCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
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
					if(!nopres || d.getPresType() == Medicine.PrescriptionType.WITHOUTPRES) {

						if(type.equals("all") || type.equals(d.getMedType().toString().toLowerCase())) {


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


	@GetMapping("/medicine/{medicine}")
	String detail(@PathVariable Medicine medicine, Model model) {

		var quantity = inventory.findByProductIdentifier(medicine.getId()) //
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);

		model.addAttribute("medicine", medicine);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));

		return "detail";
	}

}