package pharmacy.catalog;

import static org.salespointframework.core.Currencies.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import pharmacy.catalog.Medicine.IngredientType;
import pharmacy.catalog.Medicine.MedicineType;
import pharmacy.catalog.Medicine.PrescriptionType;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * A {@link DataInitializer} implementation that will create dummy data for the application on application startup.
 *
 * @author Paul Henke
 * @author Oliver Gierke
 * @see DataInitializer
 */
@Component
@Order(20)
class CatalogDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final MedicineCatalog medicineCatalog;

	CatalogDataInitializer(MedicineCatalog medicineCatalog) {

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

		LOG.info("Creating default catalog entries.");
		ArrayList<LocalDate> bbd = null;
		medicineCatalog.save(new Medicine("id", "name", "image", "usage", 10, 10, Money.of(100, EURO), bbd, 
				PrescriptionType.PRESONLY, IngredientType.BOTH, MedicineType.CAPSULE));
		
		medicineCatalog.save(new Medicine("id", "name", "image", "usage", 10, 10, Money.of(100, EURO), bbd, 
				PrescriptionType.WITHOUTPRES, IngredientType.BOTH, MedicineType.CAPSULE));

	}
}