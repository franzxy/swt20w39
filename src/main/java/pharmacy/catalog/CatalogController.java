package pharmacy.catalog;

//import org.hibernate.validator.constraints.Range;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pharmacy.catalog.Medicine.PrescriptionType;

//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
class CatalogController {

	private static final Quantity NONE = Quantity.of(0);

	private final MedicineCatalog catalog;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final BusinessTime businessTime;

	CatalogController(MedicineCatalog medicineCatalog, UniqueInventory<UniqueInventoryItem> inventory,
			BusinessTime businessTime) {

		this.catalog = medicineCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
	}


	@GetMapping("/presonly")
	String dvdCatalog(Model model) {

		model.addAttribute("catalog", catalog.findByPresType(PrescriptionType.PRESONLY));
		model.addAttribute("title", "catalog.dvd.title");

		return "catalog";
	}

	@GetMapping("/withoutpres")
	String blurayCatalog(Model model) {

		model.addAttribute("catalog", catalog.findByPresType(PrescriptionType.WITHOUTPRES));
		model.addAttribute("title", "catalog.bluray.title");

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