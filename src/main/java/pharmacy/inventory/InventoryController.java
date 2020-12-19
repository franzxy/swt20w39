package pharmacy.inventory;


import javax.validation.Valid;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pharmacy.inventory.InventoryForm;

// Straight forward?

@Controller
class InventoryController {

	private final UniqueInventory<UniqueInventoryItem> inventory;

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

		model.addAttribute("inventory", inventory.findAll());

		return "inventory";
	}
	
}

