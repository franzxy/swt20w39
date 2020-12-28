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
		ArrayList<Medicine> ingredients = null;

		medicineCatalog.save(new Medicine("Medikament 1", "med1", "Für schnelles Vergessen", 1, Money.of(100, EURO), bbd, ingredients, PrescriptionType.PRESONLY, IngredientType.SHOP, MedicineType.LIQUID));
		medicineCatalog.save(new Medicine("Medikament 2", "med2", "Gegen Durchfall", 2, Money.of(200, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.SHOP, MedicineType.CAPSULE));
		medicineCatalog.save(new Medicine("Medikament 3", "med3", "Für guten Schlaf", 3, Money.of(300, EURO), bbd, ingredients, PrescriptionType.PRESONLY, IngredientType.SHOP, MedicineType.POWDER));
		medicineCatalog.save(new Medicine("Medikament 4", "med4", "Zur völligen eskalation", 4, Money.of(400, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.SHOP, MedicineType.TABLET));
		medicineCatalog.save(new Medicine("Tinktur 5", "med5", "Tinktur", 4, Money.of(400, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.MIXTURE, MedicineType.TABLET));
		medicineCatalog.save(new Medicine("Zutat 6", "med6", "Zutat", 4, Money.of(400, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.LABOR, MedicineType.TABLET));

	}
}