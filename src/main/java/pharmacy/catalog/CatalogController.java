package pharmacy.catalog;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.MultiInventory;
import org.salespointframework.inventory.MultiInventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

@Controller
class CatalogController {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	//private final MultiInventory<MultiInventoryItem> inventory;
	//private final BusinessTime businessTime;

	CatalogController(MedicineCatalog medicineCatalog, BusinessTime businessTime, UniqueInventory inventory) {
		this.catalog = medicineCatalog;
		this.inventory = inventory;
		//this.businessTime = businessTime;
	}

	@GetMapping("/")
	public String catalog(Model model) {

		Iterator<Medicine> stock = catalog.findAll().iterator();
		ArrayList<Medicine> result = new ArrayList<>();

		while(stock.hasNext()) {
			Medicine d = stock.next();

			//if(d.getIngType() == Medicine.IngredientType.SHOP || d.getIngType() == Medicine.IngredientType.BOTH) {
				result.add(d);
			//}
		}

		model.addAttribute("catalog", result);
		model.addAttribute("searchform", new SearchForm());
		return "index";
	}


	@PostMapping("/")
	public String submitSearchInCatalog(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/search?s=" + form.getSearchTerm() + "&i=shop";
	}

	@GetMapping("/search")
	//p=presType, m=medType, i=ingType
	public String searchCatalog(@RequestParam(name="e", defaultValue = "false") boolean empty, @RequestParam(name="s", required=true, defaultValue = "") String searchTerm, @RequestParam(name="p", defaultValue = "false") boolean noPres, @RequestParam(name="i", defaultValue = "shop") String ingType, @RequestParam(name="m", defaultValue = "all") String medType, Model model) {
		model.addAttribute("searchform", new SearchForm());

		if(empty) {
			model.addAttribute("Titel", "Erweiterte Suche");
			return "search";
		}

		String[] search = searchTerm.toLowerCase().split(" ");
		ArrayList<Medicine> result = new ArrayList<>();
		Iterator<Medicine> stock = catalog.findAll().iterator();


		while (stock.hasNext()) {
			Medicine d = stock.next();

			for (int i = 0; i < search.length; i++) {

				if (searchTerm.equals("") || d.getName().toLowerCase().contains(search[i])) {

					//if(!noPres || d.getPresType().equals("Frei Verkäuflich")) {

						//if(medType.equals("all") || d.getMedType().equals(medType)) {

							//if(ingType.equals("all") || d.getIngType().toString().toLowerCase().equals(ingType) || (  (ingType.equals("shop")||ingType.equals("labor"))   &&   d.getIngType() == Medicine.IngredientType.BOTH  )) {

								if (!result.contains(d)) {
									result.add(d);
								}
							//}
						//}
					//}
				}
			}
		}

		model.addAttribute("catalog", result);

		if(result.size() == 0) {
			model.addAttribute("Suchbegriff", "Keine Ergebnisse");
			model.addAttribute("Titel", "Keine Ergebnisse");
		}

		else if(searchTerm.equals("")) {
			model.addAttribute("Suchbegriff", "Alle Medikamente");
			model.addAttribute("Titel", "Alle Medikamente");
		}

		else {
			model.addAttribute("Suchbegriff", "Ergebnisse für \"" + searchTerm + "\":");
			model.addAttribute("Titel", "Ergebnisse für \"" + searchTerm + "\"");
		}

		return "search";
	}

	@PostMapping("/search")
	public String submitSearch(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		if(form.getIngType() == null) form.setIngType("shop");
		return "redirect:/search?s=" + form.getSearchTerm() + "&p=" + form.getNoPres() + "&m=" + form.getMedType() + "&i=" + form.getIngType();
	}

	@GetMapping("/medicine/{medicine}")
	public String detail(@PathVariable Medicine medicine, Model model) {
		Quantity q=Quantity.of(0);
		if(inventory.findByProductIdentifier(medicine.getId()).isPresent()){
			q = inventory.findByProductIdentifier(medicine.getId()).get().getQuantity();
		}

		model.addAttribute("medicine" , medicine);
		model.addAttribute("quantity", q); //TODO geht noch nicht
		//model.addAttribute("orderable", q.isGreaterThan(NONE));
		model.addAttribute("orderable", true);

		return "detail";
	}
}