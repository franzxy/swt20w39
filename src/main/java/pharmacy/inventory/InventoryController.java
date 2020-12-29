package pharmacy.inventory;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
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

import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;




// Straight forward?

@Controller
class InventoryController {
	@Autowired
	//@OneToOne(cascade = CascadeType.ALL)
	//@ManyToMany(cascade = CascadeType.PERSIST)
	private UniqueInventory<UniqueInventoryItem> inventory;
	@Autowired
	private MedicineCatalog medicineCatalog;
	InventoryController(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog) {
		this.inventory = inventory;
		this.medicineCatalog=medicineCatalog;
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
	
		
		
		medicineCatalog.save(formular.toMedicine());//new UniqueInventoryItem((Medicine)formular.toMedicine(), Quantity.of(formular.getQuantity())));
		Medicine med= new Medicine("1111",				"richtig gutes zeug", Money.of(23, "EUR"), Money.of(10, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"), 0.03,  false, "");
		UniqueInventoryItem uni = new UniqueInventoryItem((Medicine)med, Quantity.of(10));
		//inventory.save(uni);
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

