package pharmacy.inventory;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class InventoryController {

	private final UniqueInventory<UniqueInventoryItem> inventory;

	InventoryController(UniqueInventory<UniqueInventoryItem> inventory) {
		this.inventory = inventory;
	}

	@GetMapping("/")
	public String index() {
		return "inventory";
	}
}
