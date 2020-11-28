package pharmacy.inventory;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import pharmacy.inventory.Medicine.MedicineType;

@Controller
class InventoryController {

	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	private final UniqueInventory<UniqueInventoryItem> inventory;

	InventoryController(MedicineCatalog medicineCatalog, UniqueInventory<UniqueInventoryItem> inventory,
			BusinessTime businessTime) {

		this.catalog = medicineCatalog;
		this.inventory = inventory;
	}

	@GetMapping("/prescriptiononly")
	String prescriptiononlyCatalog(Model model) {

		model.addAttribute("catalog", catalog.findByType(MedicineType.PRESCRIPTIONONLY));
		model.addAttribute("title", "catalog.prescriptiononly.title");

		return "catalog";
	}

	@GetMapping("/withoutprescription")
	String withoutprescriptionCatalog(Model model) {

		model.addAttribute("catalog", catalog.findByType(MedicineType.WITHOUTPRESCRIPTION));
		model.addAttribute("title", "catalog.withoutprescription.title");

		return "catalog";
	}

	// (｡◕‿◕｡)
	// Befindet sich die angesurfte Url in der Form /foo/5 statt /foo?bar=5 so muss man @PathVariable benutzen
	// Lektüre: http://spring.io/blog/2009/03/08/rest-in-spring-3-mvc/
	@GetMapping("/medicine/{medicine}")
	String detail(@PathVariable Medicine medicine, Model model) {

		var quantity = inventory.findByProductIdentifier(medicine.getId()) //
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);

		model.addAttribute("medicine", medicine);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));

		return "detail";
	}
}