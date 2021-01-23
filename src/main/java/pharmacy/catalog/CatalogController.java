package pharmacy.catalog;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
class CatalogController {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	private Cart cart;

	CatalogController(MedicineCatalog medicineCatalog, BusinessTime businessTime, UniqueInventory<UniqueInventoryItem> inventory, Cart cart) {
		this.catalog = medicineCatalog;
		this.inventory = inventory;
		this.cart = cart;
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
		Set<String> newTags = new HashSet<>();

		if(noPres) i = catalog.findByPresonly(false).iterator();
		else i = catalog.findAll().iterator();

		if(searchTerm.equals("")) {
			while(i.hasNext()) {
				Medicine m = i.next();
				Set<String> tags = m.getCategories().toSet();
				newTags.addAll(tags);

				if(tag.equals("") || tags.contains(tag)) result.add(m);

			}
		} else {
			String[] search = searchTerm.toLowerCase().split(" ");

			while(i.hasNext()) {
				Medicine m = i.next();
				Set<String> tags = m.getCategories().toSet();

				for(String s : search) {
					if (m.getName().toLowerCase().contains(s)) {
						newTags.addAll(tags);

						if(tag.equals("") || tags.contains(tag))
							result.add(m);

					}
				}
			}
		}

		String header;

		if(result.size() == 0) {
			header = "Keine Ergebnisse für";
		} else {
			header = "Ergebnisse für";
		}

		if(!searchTerm.equals("")) model.addAttribute("header", header);

		model.addAttribute("tags", newTags);
		model.addAttribute("oldTerm", searchTerm);
		model.addAttribute("oldTag", tag);
		model.addAttribute("noPres", noPres);
		model.addAttribute("title", "Apotheke");

		//Add quantity from inventory
		HashMap<String, Integer> availability = new HashMap<String, Integer>();
		ArrayList<Medicine> optimisedres = new ArrayList<>();
		result.forEach(Med->{
			int quan = inventory.findByProduct(Med).get().getQuantity().getAmount().intValue();

			for(CartItem c : cart.toList()) {
				if(c.getProductName().equals(Med.getName())) {
					quan = quan - c.getQuantity().getAmount().intValue();
				}
			}

			availability.put(Med.getId().getIdentifier(), quan);
			//remove Items that aren't available
			if(quan>0)optimisedres.add(Med);
		});

		model.addAttribute("availability", availability);
		model.addAttribute("catalog", optimisedres);

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
		Quantity q = Quantity.of(0);
		
		if(inventory.findByProduct(medicine).isPresent()){
			q = inventory.findByProduct(medicine).get().getQuantity();
		}


		int quan = q.getAmount().intValue();

		for(CartItem c : cart.toList()) {
			if(c.getProductName().equals(medicine.getName())) {
				quan = quan - c.getQuantity().getAmount().intValue();
			}
		}


		model.addAttribute("medicine" , medicine);
		model.addAttribute("available", quan);

		return "detail";
	}
}