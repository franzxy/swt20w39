package pharmacy.inventory;

import java.util.ArrayList;
import java.util.Date;

import static org.salespointframework.core.Currencies.*;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pharmacy.inventory.Medicine.IngredientType;
import pharmacy.inventory.Medicine.MedicineType;
import pharmacy.inventory.Medicine.PrescriptionType;

/**
 * A {@link DataInitializer} implementation that will create dummy data for the application on application startup.
 *
 * @author Paul Henke
 * @author Oliver Gierke
 * @see DataInitializer
 */
@Component
@Order(20)
class InventoryDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(InventoryDataInitializer.class);

	private final MedicineCatalog medicineCatalog;

	InventoryDataInitializer(MedicineCatalog medicineCatalog) {

		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");

		this.medicineCatalog = medicineCatalog;
	}

	/*
	 * (non-Javadoc)
	 * @see org.salespointframework.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		if (medicineCatalog.findAll().iterator().hasNext()) {
			return;
		}

		LOG.info("Creating default inventory entries.");
		
		ArrayList<Date> testmhd = null;
		ArrayList<String> testingredients = null;
		
		medicineCatalog.save(new Medicine("id", "name", Money.of(100, EURO), "usage", "image", 10, 10, testmhd, testingredients, 
				IngredientType.LABOR, PrescriptionType.PRESCRIPTIONONLY, MedicineType.CAPSULE));


	}
}