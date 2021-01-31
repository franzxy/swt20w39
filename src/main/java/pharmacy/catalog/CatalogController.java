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
import pharmacy.finances.AccountancyAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Ein Controller für die Katalogansicht.
 * Nutzt {@link MedicineCatalog}.
 * @author Falk Natkowski
 */
@Controller
class CatalogController {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	private Cart cart;

	/**
	 * Initialisiert den Katalog-Controller
	 * @param {@link MedicineCatalog}
	 * @param {@link businessTime}
	 * @param {@link inventory}
	 * @param {@link cart}
	 */
	CatalogController(MedicineCatalog medicineCatalog, BusinessTime businessTime, UniqueInventory<UniqueInventoryItem> inventory, Cart cart) {
		this.catalog = medicineCatalog;
		this.inventory = inventory;
		this.cart = cart;
	}

	/**
	 * GET-Mapping für Katalog-Controller. Per Link-Parameter können Elemente gefiltert werden.
	 * Parameter müssen unbedingt per URI-Encoder in UTF-8 codiert werden, um Umlaute zu unterstützen!
	 * @param searchTerm Suchbegriff, s=
	 * @param tag Kategorie, t=
	 * @param noPres Verschreibungspflichtig oder nicht, p=
	 * @param model
	 * @return Index-Template
	 */
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

		//Add quantity from inventory
		HashMap<String, Integer> availability = new HashMap<String, Integer>();
		ArrayList<Medicine> optimisedres = new ArrayList<>();
		result.forEach(Med->{
			int quan = inventory.findByProduct(Med).get().getQuantity().getAmount().intValue();

			//Check for items in cart to calculate quantity
			for(CartItem c : cart.toList()) {
				if(c.getProductName().equals(Med.getName())) {
					quan = quan - c.getQuantity().getAmount().intValue();
				}
			}

			availability.put(Med.getId().getIdentifier(), quan);
			//remove Items that aren't available
			if(quan>0)optimisedres.add(Med);
		});

		//Generate header
		String header = " für \"" + searchTerm + "\"";

		if(!tag.equals("")) {
			String tagopt = tag.substring(0, 1).toUpperCase() + tag.substring(1);
			header += " in Kategorie " + tagopt;
		}

		if(noPres) {
			header += ", Rezeptfrei";
		}

		if(optimisedres.size() == 0) {
			header = "Keine Ergebnisse " + header + ".";

		} else {
			header = "Ergebnisse" + header + ":";
		}

		if(!searchTerm.equals("")) model.addAttribute("header", header);

		model.addAttribute("tags", newTags);
		model.addAttribute("oldTerm", searchTerm);
		model.addAttribute("oldTag", tag);
		model.addAttribute("noPres", noPres);
		model.addAttribute("title", "Apotheke");

		model.addAttribute("availability", availability);
		model.addAttribute("catalog", optimisedres);

		return "index";
	}


	/**
	 * POST-Mapping für Katalog-Controller. Nutzt {@link SearchForm} um Suche in Katalog auszulösen.
	 * Suchparameter werden automatisch in UTF-8 kodiert.
	 * @param bar Vorheriger Suchbegriff
	 * @param form Such-Formular
	 * @param model
	 * @return Redirect auf / mit Suchparametern im link.
	 */
	@PostMapping("/")
	public String submitSearchInCatalog(@RequestParam("searchbar") String bar, @ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);
		form.setSearchTerm(bar);

		String UTFTerm = "";
		String UTFTag = "";

		try {
			if(!(form.getSearchTerm() == null)) UTFTerm = URLEncoder.encode(form.getSearchTerm(), "UTF-8");
			if(!(form.getTag() == null)) UTFTag = URLEncoder.encode(form.getTag(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "redirect:/?s=" + UTFTerm + "&p=" + form.getNoPres() + "&t=" + UTFTag;
	}

	/**
	 * GET-Mapping für Detailseite von einzelnem Produkt
	 * @param medicine Anzuzeigendes Produkt
	 * @param model
	 * @return
	 */
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