package pharmacy.catalog;

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
import java.util.Set;

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
	public String catalog(@RequestParam(name="s", required=true, defaultValue = "") String searchTerm,
	                      @RequestParam(name="t", required=true, defaultValue = "") String tag,
	                      @RequestParam(name="p", defaultValue = "false") boolean noPres,
	                      Model model) {

		model.addAttribute("searchform", new SearchForm());

		if (searchTerm.equals("null")) searchTerm = "";
		if (tag.equals("null")) tag = "";

		ArrayList<Medicine> result = new ArrayList<>();
		Iterator<Medicine> i;

		if(noPres) {
			i = catalog.findByPresonly(false).iterator();
		} else {
			i = catalog.findAll().iterator();
		}

		if(searchTerm.equals("")) {
			while(i.hasNext()) {
				result.add(i.next());
			}
		}

		else {
			String[] search = searchTerm.toLowerCase().split(" ");

			while(i.hasNext()) {
				Medicine m = i.next();
				Set<String> tags = m.getCategories().toSet();

				for(String s : search) {
					if (m.getName().toLowerCase().contains(s)) {
						if(tag.equals("") || tags.contains(tag)) {
							result.add(m);

						}
					}
				}
			}
		}

		ArrayList<String> newTags = new ArrayList<>();
		for (Medicine m : result) {
			for (String s : m.getCategories()) {
				if(!newTags.contains(s)) {
					newTags.add(s);
				}
			}
		}

		if(tag.equals("") && !searchTerm.equals("") && result.size() > 0) {
			model.addAttribute("tags", newTags);
			model.addAttribute("showtags", true);

		}

		String header = "";

		if(result.size() == 0) header = "Keine Ergebnisse";

		else {
			header = "Ergebnisse für \"" +  searchTerm + "\":";
			if(!tag.equals("")) header = "Ergebnisse für \"" +  searchTerm + "\" in Kategorie \"" + tag +"\":";
		}

		if(!searchTerm.equals("")) model.addAttribute("header", header);

		model.addAttribute("oldTerm", searchTerm);
		model.addAttribute("nopres", noPres);
		model.addAttribute("catalog", result);
		model.addAttribute("title", "Apotheke");

		return "index";
	}

	@PostMapping("/")
	public String submitSearchInCatalog(@RequestParam("searchbar") String bar, @ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		form.setSearchTerm(bar);

		return "redirect:/?s=" + form.getSearchTerm() + "&p=" + form.getNoPres() + "&t=" + form.getTag();
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