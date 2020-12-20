package pharmacy.inventory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import org.javamoney.moneta.Money;

import org.salespointframework.inventory.*;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pharmacy.catalog.Medicine;




// Straight forward?

@Controller
class InventoryController {
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	InventoryController(UniqueInventory<UniqueInventoryItem> inventory) {
		this.inventory = inventory;
	}

	/**
	 * Displays all {@link InventoryItem}s in the system
	 *
	 * @param model will never be {@literal null}.
	 * @return the view name.
	 */
	
	@GetMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String inventory(Model model) {
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());
		return "inventory";
	}
	@PostMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String filtern( Model model) {
		model.addAttribute("inventory", inventory.findAll().toList());
		//model.addAttribute("formular", new MedicineForm());
		return "inventory";
	}

	@GetMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String premed(Model model) {
		model.addAttribute("formular", new MedicineForm());
		return "redirect:/inventory";
	}



	@PostMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String addingMedicine(@ModelAttribute MedicineForm formular, Model model) {
		ArrayList<Medicine>  ingredients =new ArrayList<Medicine>();
		for( UniqueInventoryItem p : this.inventory.findAll().toList()){
			if(p.getProduct().getId().toString().equals(formular.getIngredient1())){
				ingredients.add((Medicine)p.getProduct());
			}
			if(p.getProduct().getId().toString().equals(formular.getIngredient2())){
				ingredients.add((Medicine)p.getProduct());
			}
			if(p.getProduct().getId().toString().equals(formular.getIngredient3())){
				ingredients.add((Medicine)p.getProduct());
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Medicine med = new Medicine("asd", formular.getName(), "asd", formular.getUsage(), formular.getSize(),
				Money.of(formular.getPrice(), "EUR"), LocalDate.parse(formular.getBbd(), formatter), ingredients,
				formular.getPresType(), formular.getIngType(), formular.getMedType());
		UniqueInventoryItem in=new UniqueInventoryItem(med, Quantity.of(formular.getAmount()));
		//this.inventory.save(in);
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());

		return "redirect:/inventory";
	}
	
	@GetMapping("/addorigmed")
	@PreAuthorize("hasRole('BOSS')")
	String preorigmed(Model model) {
		return "redirect:/inventory#newmed";
	}
	@PostMapping("/addorigmed")
	@PreAuthorize("hasRole('BOSS')")
	String addingMedicinetomixtures(@ModelAttribute MedicineForm formular, Model model) {
		
		
		
		//model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", formular);
		return "redirect:/addmed";
	}
}

