package pharmacy.inventory;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pharmacy.catalog.MedicineCatalog;
/**
 * Eine Implementierung des {@link DataInitializer} welche notwendig ist damit gewisse Objekte erzeugt werden beim 
 * start.
 * @author Lukas Luger
 */
@Component
@Order(20)
class InventoryInitializer implements DataInitializer {

	@OneToOne(cascade = CascadeType.ALL)
	private final UniqueInventory<UniqueInventoryItem> inventory;
	
	private final MedicineCatalog medicineCatalog;
	/**
	 * Dient zur Initialisierung.
	 * @param inventory
	 * @param medicineCatalog
	 */
	InventoryInitializer(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog) {

		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");

		this.inventory = inventory;
		this.medicineCatalog = medicineCatalog;

	}
	/**
	 * Diese Methode ist leer, aber notwendig.
	 */
	@Override
	public void initialize() {

		medicineCatalog.findAll().forEach(medicine -> {

			if (inventory.findByProduct(medicine).isEmpty()) {
					inventory.save(new UniqueInventoryItem(medicine, Quantity.of(medicine.getQuantity())));
			}
		});
		
	}
}