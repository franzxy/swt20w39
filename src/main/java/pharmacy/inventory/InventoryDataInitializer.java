package pharmacy.inventory;

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

		medicineCatalog.save(new Medicine("prescription only med a", "Bildpfad", Money.of(100, EURO),"Kopfschmerzen", IngredientType.LABOR, MedicineType.PRESCRIPTIONONLY));

		medicineCatalog.save(new Medicine("without prescription med b", "Bildpfad", Money.of(6.99, EURO),"Krebs", IngredientType.SHOP, MedicineType.WITHOUTPRESCRIPTION));

	}
}