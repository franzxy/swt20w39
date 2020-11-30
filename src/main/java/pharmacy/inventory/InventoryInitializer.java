package pharmacy.inventory;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pharmacy.catalog.MedicineCatalog;

/**
 * A {@link DataInitializer} implementation that will create dummy data for the application on application startup.
 *
 * @author Paul Henke
 * @author Oliver Gierke
 * @see DataInitializer
 */
@Component
@Order(20)
class InventoryInitializer implements DataInitializer {

	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final MedicineCatalog medicineCatalog;

	InventoryInitializer(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog) {

		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");

		this.inventory = inventory;
		this.medicineCatalog = medicineCatalog;
	}

	/*
	 * (non-Javadoc)
	 * @see org.salespointframework.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		// (｡◕‿◕｡)
		// Über alle Discs iterieren und jeweils ein InventoryItem mit der Quantity 10 setzen
		// Das heißt: Von jeder Disc sind 10 Stück im Inventar.

		medicineCatalog.findAll().forEach(medicine -> {

			// Try to find an InventoryItem for the project and create a default one with 10 items if none available
			if (inventory.findByProduct(medicine).isEmpty()) {
				inventory.save(new UniqueInventoryItem(medicine, Quantity.of(10)));
			}
		});
	}
}