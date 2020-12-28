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
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pharmacy.catalog.Medicine;
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
	//@OneToMany(cascade = CascadeType.ALL)
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

		//medicineCatalog.findAll().forEach(medicine -> {

			// Try to find an InventoryItem for the project and create a default one with 10 items if none available
			//if (inventory.findByProduct(medicine).isEmpty()) {
			//	try{
			//		if(medicine!=null){
					//Medicine med= new Medicine("BLUBBLUBBLUB", "BLALALALA", Money.of(23,"EUR"), Money.of(20,"EUR"),  Arrays.asList("bla", "blub"), 0.03, Metric.KILOGRAM, false, "");
			//		new UniqueInventoryItem(medicine, Quantity.of(10, medicine.getMetric()));
				//}}
				//catch(IllegalArgumentException e){
				//	System.out.println("--------FAIL: "+e.getMessage());
				//}
				
			//}
		//});
		//Medicine med= new Medicine("BLUBBLUBBLUB", "BLALALALA", Money.of(23,"EUR"), Money.of(20,"EUR"),  Arrays.asList("bla", "blub"), 0.03, Metric.KILOGRAM, false, "dsfdsafdsa");
		//inventory.save( new UniqueInventoryItem(med, Quantity.of(10, med.getMetric())));
		
	}
}