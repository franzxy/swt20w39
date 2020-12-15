package pharmacy.search;

import org.javamoney.moneta.Money;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import static org.salespointframework.core.Currencies.EURO;


@Controller

public class SearchController {


	@GetMapping("/searchform")
	@PreAuthorize("hasRole('USER')")
	public String searchForm(Model model) {
		model.addAttribute("searchform", new SearchForm());
		return "searchform";
	}

	@PostMapping("/searchform")
	public String submitSearchForm(@ModelAttribute SearchForm form, Model model) {
		model.addAttribute("SearchForm", form);

		return "redirect:/search?searchTerm=" + form.getSearchTerm() + "&p=" + form.getNoPres();

	}

	@GetMapping("/search")
	String searchCatalog(@RequestParam(name="searchTerm", required=true, defaultValue = "") String searchTerm, @RequestParam(name="p", defaultValue = "false") boolean nopres, MedicineCatalog catalog, Model model) {

		if(searchTerm.equals("")) return "redirect:/searchform";


		String[] search = searchTerm.split(" ");

		ArrayList<Medicine> result = new ArrayList<>();

		Iterator<Medicine> stock;


		//Temporär
		ArrayList<LocalDate> bbd = null;
		ArrayList<Medicine> ingredients = null;
		ArrayList<Medicine> tempstock = new ArrayList<>();
		if (!nopres) {
			tempstock.add(new Medicine("123", "Medikament 1", "medikamente", "usage", 10, 10, Money.of(5, EURO), bbd, ingredients, Medicine.PrescriptionType.PRESONLY, Medicine.IngredientType.BOTH, Medicine.MedicineType.CAPSULE));
			tempstock.add(new Medicine("456", "Medikament 2", "image", "usage", 10, 10, Money.of(8, EURO), bbd, ingredients, Medicine.PrescriptionType.PRESONLY, Medicine.IngredientType.BOTH, Medicine.MedicineType.CAPSULE));
		}
		tempstock.add(new Medicine("789", "Medikament 3", "image", "usage", 10, 10, Money.of(10, EURO), bbd, ingredients, Medicine.PrescriptionType.WITHOUTPRES, Medicine.IngredientType.BOTH, Medicine.MedicineType.CAPSULE));
		stock = tempstock.iterator();

		/* Suchfunktion wenn Katalog-Integration funktioniert
		if(nopres) stock = catalog.findByPresType(Medicine.PrescriptionType.WITHOUTPRES).iterator();
		else stock = catalog.findAll().iterator();
		*/

		while (stock.hasNext()) {
			Medicine d = stock.next();

			for (int i = 0; i < search.length; i++) {
				if (d.getName().toLowerCase().contains(search[i].toLowerCase())) {
					if (!result.contains(d)) {
						result.add(d);
					}
				}
			}
		}

		model.addAttribute("catalog", result);
		model.addAttribute("title", "Ergebnisse für " + searchTerm);

		return "searchresult";
	}
}
