package pharmacy.catalog;

import org.javamoney.moneta.Money;
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

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
	public String catalog(@RequestParam(name="s", required=true, defaultValue = "") String searchTerm, @RequestParam(name="p", defaultValue = "false") boolean noPres, Model model) {
		model.addAttribute("searchform", new SearchForm());

		Iterator<Medicine> stock = catalog.findAll().iterator();
		ArrayList<Medicine> result = new ArrayList<>();

		if(searchTerm.equals("") && noPres == false) {
			while (stock.hasNext()) {
				Medicine d = stock.next();
				result.add(d);
			}

		} else if(searchTerm.equals("") && noPres == true) {
			while (stock.hasNext()) {
				Medicine d = stock.next();
				if (!d.isPresonly()) {
					if (d.getQuantity() > 0) {
						if (!result.contains(d)) {
							result.add(d);
						}
					}
				}
			}

		} else {


			String[] search = searchTerm.toLowerCase().split(" ");

			while (stock.hasNext()) {
				Medicine d = stock.next();

				for (int i = 0; i < search.length; i++) {

					if (d.getName().toLowerCase().contains(search[i])) {
						if(!noPres || !d.isPresonly()) {
							if (d.getQuantity() > 0) {
								if (!result.contains(d)) {
									result.add(d);
								}
							}
						}
					}

					List<String> tags = d.getCategories().toList();

					for(String t : tags) {
						for (int ii = 0; ii < search.length; ii++) {
							if (t.toLowerCase().contains(search[i])) {
								if(!noPres || !d.isPresonly()) {
									if (d.getQuantity() > 0) {
										if (!result.contains(d)) {
											result.add(d);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		model.addAttribute("catalog", result);
		model.addAttribute("Titel", "Apotheke");

		if(!searchTerm.equals("")) {
			model.addAttribute("Suchbegriff", "Ergebnisse für \"" + searchTerm + "\":");
			model.addAttribute("Titel", "Ergebnisse für \"" + searchTerm + "\"");
		}


		if(result.size() == 0) {
			model.addAttribute("Suchbegriff", "Keine Ergebnisse für \"" + searchTerm + "\"");
			model.addAttribute("Titel", "Keine Ergebnisse");
		}



		return "index";
	}


	@PostMapping("/")
	public String submitSearchInCatalog(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/?s=" + form.getSearchTerm() + "&p=" + form.getNoPres();
	}

	@PostMapping("/search")
	public String submitSearch(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		return "redirect:/?s=" + form.getSearchTerm();

	}

	@GetMapping("/medicine/{medicine}")
	public String detail(@PathVariable Medicine medicine, Model model) {
		Quantity q=Quantity.of(0);
		if(inventory.findByProductIdentifier(medicine.getId()).isPresent()){
			q = inventory.findByProductIdentifier(medicine.getId()).get().getQuantity();
		}

		model.addAttribute("medicine" , medicine);
		model.addAttribute("orderable", true);

		return "detail";
	}
}