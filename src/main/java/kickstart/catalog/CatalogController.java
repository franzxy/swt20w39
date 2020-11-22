package kickstart.catalog;

import kickstart.catalog.Medicine.MedicineType;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	// (｡◕‿◕｡)
	// Der Katalog bzw die Datenbank "weiß" nicht, dass die Disc mit einem Kommentar versehen wurde,
	// deswegen wird die update-Methode aufgerufen
	@PostMapping("/medicine/{medicine}/comments")
	public String comment(@PathVariable Medicine medicine, @Valid CommentAndRating payload) {

		medicine.addComment(payload.toComment(businessTime.getTime()));
		catalog.save(medicine);

		return "redirect:/medicine/" + medicine.getId();
	}

	/**
	 * Describes the payload to be expected to add a comment.
	 *
	 * @author Oliver Gierke
	 */
	interface CommentAndRating {

		@NotEmpty
		String getComment();

		@Range(min = 1, max = 5)
		int getRating();

		default Comment toComment(LocalDateTime time) {
			return new Comment(getComment(), getRating(), time);
		}
	}
}