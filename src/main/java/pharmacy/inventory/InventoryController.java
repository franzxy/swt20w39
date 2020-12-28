package pharmacy.inventory;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




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
	
		
		UniqueInventoryItem in=new UniqueInventoryItem(formular.toMedicine(), Quantity.of(formular.getQuantity()));
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

