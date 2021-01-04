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

	@PostMapping("/tagsearch")
	public String tagSearch(@RequestParam("tempsearch") String tempsearch, @RequestParam("tag") String tag) {
		return "redirect:/?s=" + tempsearch + "&t=" + tag;
	}


	@GetMapping("/")
	public String catalog(@RequestParam(name="s", required=true, defaultValue = "") String searchTerm, @RequestParam(name="t", required=true, defaultValue = "") String tag, @RequestParam(name="p", defaultValue = "false") boolean noPres, Model model) {
		model.addAttribute("searchform", new SearchForm());

		Iterator<Medicine> stock = catalog.findAll().iterator();
		ArrayList<Medicine> result = new ArrayList<>();

		if(searchTerm.equals("null")) searchTerm = "";

		if(searchTerm.equals("")) {
			while (stock.hasNext()) {
				Medicine d = stock.next();
				if (!d.isPresonly() || !noPres) {
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
								if(tag.equals("")) {
									if (!result.contains(d)) {
										result.add(d);
									}

								} else {

									List<String> tags = d.getCategories().toList();

									for(String t : tags) {

										if(tag.equals(t)) {
											if (!result.contains(d)) {
												result.add(d);

											}
										}
									}
								}
							}
						}
					}

					/*List<String> tags = d.getCategories().toList();

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
					}*/
				}
			}
		}

		ArrayList<String> tags = new ArrayList<>();
		for(Medicine p : result) {
			for(String s : p.getCategories()) {
				if(!tags.contains(s)) {
					tags.add(s);
				}
			}
		}

		model.addAttribute("tempTerm", searchTerm);


		String resultTitle = "";

		if(result.size() == 0) {
			resultTitle = "Keine Ergebnisse für \"" + searchTerm + "\"";

			if(!tag.equals("")) {
				resultTitle = resultTitle + " mit Tag \"" + tag + "\"";

			}
		} else if(!searchTerm.equals("")) {
			resultTitle = "Ergebnisse für \"" + searchTerm + "\"";

			if(!tag.equals("")) {
				resultTitle = resultTitle + " mit Tag \"" + tag + "\"";

			}
		}

		model.addAttribute("Suchbegriff", resultTitle);

		if(tag.equals("") && !searchTerm.equals("")) model.addAttribute("tags", tags);

		model.addAttribute("catalog", result);
		model.addAttribute("Titel", "Apotheke");

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