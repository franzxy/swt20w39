package pharmacy.catalog;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import pharmacy.AbstractIntegrationTests;

class CatalogControllerIntegrationTests extends AbstractIntegrationTests {


	@Autowired CatalogController controller;

	@Test
	@SuppressWarnings("unchecked")
	public void controllerIntegrationTest() {

		Model model = new ExtendedModelMap();

		String returnedView = controller.catalog("", "", false, model);

		assertThat(returnedView).isEqualTo("index");

		Iterable<Object> object = (Iterable<Object>) model.asMap().get("catalog");

		assertThat(object).hasSize(32);
	}

	@Test
	public void searchTest() {

		Model model = new ExtendedModelMap();

		String returnedView = controller.catalog("1", "", false, model);

		Iterable<Object> object = (Iterable<Object>) model.asMap().get("catalog");

		assertThat(object).hasSize(2);
	}


}
