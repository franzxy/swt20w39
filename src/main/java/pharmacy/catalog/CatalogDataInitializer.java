package pharmacy.catalog;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pharmacy.catalog.Medicine.IngredientType;
import pharmacy.catalog.Medicine.MedicineType;
import pharmacy.catalog.Medicine.PrescriptionType;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.salespointframework.core.Currencies.EURO;

@Component
@Order(20)
class CatalogDataInitializer implements DataInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final MedicineCatalog medicineCatalog;

	CatalogDataInitializer(MedicineCatalog medicineCatalog) {
		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");
		this.medicineCatalog = medicineCatalog;
	}

	@Override
	public void initialize() {

		if (medicineCatalog.findAll().iterator().hasNext()) {
			return;
		}

		LOG.info("Creating default catalog entries.");
		ArrayList<LocalDate> bbd = null;
		ArrayList<Medicine> ingredients = null;

		medicineCatalog.save(new Medicine("Medikament 1", "med1", "usage1", 1, Money.of(100, EURO), bbd, ingredients, PrescriptionType.PRESONLY, IngredientType.LABOR, MedicineType.LIQUID));
		medicineCatalog.save(new Medicine("Medikament 2", "med2", "usage2", 2, Money.of(200, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.MIXTURE, MedicineType.CAPSULE));
		medicineCatalog.save(new Medicine("Medikament 3", "med3", "usage3", 3, Money.of(300, EURO), bbd, ingredients, PrescriptionType.PRESONLY, IngredientType.SHOP, MedicineType.POWDER));
		medicineCatalog.save(new Medicine("Medikament 4", "med4", "usage4", 4, Money.of(400, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.BOTH, MedicineType.TABLET));

		medicineCatalog.save(new Medicine("Zentiva Ibuflam 400mg", "ibu400", "Schmerzmittel, 10 Stück, Wirkstoff Ibuprofen",
				10, Money.of(4.99, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.SHOP, MedicineType.TABLET));

		medicineCatalog.save(new Medicine("Zentiva Ibuflam 800mg", "ibu800", "Schmerzmittel, 50 Stück, Wirkstoff Ibuprofen",
				20, Money.of(7.99, EURO), bbd, ingredients, PrescriptionType.PRESONLY, IngredientType.SHOP, MedicineType.TABLET));

		medicineCatalog.save(new Medicine("Ratiopharm Nasenspray Erwachsene", "nse", "Nasenspray für Erwachsene, 10ml, Wirkstoff Xylometazolin",
				10, Money.of(1.99, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.SHOP, MedicineType.LIQUID));

		medicineCatalog.save(new Medicine("Ratiopharm Nasenspray Kinder", "nsk", "Nasenspray für Kinder, 10ml, Wirkstoff Xylometazolin",
				10, Money.of(1.99, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.SHOP, MedicineType.LIQUID));

		medicineCatalog.save(new Medicine("Bepanthen Wund- und Heilsalbe", "bep", "Wundheilsalbe für Haut, 50ml, Wirkstoff Dexpanthenol",
				50, Money.of(2.59, EURO), bbd, ingredients, PrescriptionType.PRESONLY, IngredientType.SHOP, MedicineType.OINTMENT));

		medicineCatalog.save(new Medicine("Tinktur 5", "med5", "Tinktur", 4, Money.of(400, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.MIXTURE, MedicineType.TABLET));
		medicineCatalog.save(new Medicine("Zutat 6", "med6", "Zutat", 4, Money.of(400, EURO), bbd, ingredients, PrescriptionType.WITHOUTPRES, IngredientType.LABOR, MedicineType.TABLET));


	}
}