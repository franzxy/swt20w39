package pharmacy.catalog;

import static org.salespointframework.core.Currencies.*;

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

		medicineCatalog.save(new Medicine("Last Action Hero", "lac", Money.of(100, EURO), "Äktschn/Comedy", 
				PrescriptionType.DVD));

		medicineCatalog.save(new Medicine("Secretary", "secretary", Money.of(6.99, EURO), "Political Drama", 
				PrescriptionType.BLURAY));

	}
}