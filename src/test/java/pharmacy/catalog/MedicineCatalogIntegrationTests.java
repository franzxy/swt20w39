package pharmacy.catalog;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pharmacy.AbstractIntegrationTests;
import pharmacy.Pharmacy;
import pharmacy.catalog.Medicine;

class MedicineCatalogIntegrationTests extends AbstractIntegrationTests {


	@Autowired MedicineCatalog catalog;

	@Test
	void findAllPrescriptionMeds() {

		Iterable<Medicine> result = catalog.findByPresonly(true);
		assertThat(result).hasSize(18);
	}


	@Test
	void medsHaveCategoriesAssigned() {

		for (Medicine m : catalog.findByPresonly(true)) {
			assertThat(!(m.getCategories()).isEmpty());
		}
	}

}
