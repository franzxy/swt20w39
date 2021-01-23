package pharmacy.inventory;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.*;
import org.salespointframework.quantity.Metric;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;

@Component
@Order(20)
class InventoryInitializer implements DataInitializer {
	@OneToOne(cascade = CascadeType.ALL)
	private final UniqueInventory<UniqueInventoryItem> inventory;
	
	private final MedicineCatalog medicineCatalog;
	
	InventoryInitializer(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog) {

		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");
		this.inventory = inventory;
		this.medicineCatalog = medicineCatalog;
	}

	@Override
	public void initialize() {

		medicineCatalog.findAll().forEach(medicine -> {

			if (inventory.findByProduct(medicine).isEmpty()) {
					inventory.save(new UniqueInventoryItem((Medicine)medicine, Quantity.of(((Medicine)medicine).getQuantity())));
			}
		});
		
	}
}