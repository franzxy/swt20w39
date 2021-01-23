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
	@SuppressWarnings("unchecked")
	public void searchTest() {

		Model model = new ExtendedModelMap();

		//Test PrescriptionType working
		String returnedView = controller.catalog("", "", true, model);
		Iterable<Object> object = (Iterable<Object>) model.asMap().get("catalog");
		assertThat(object).hasSize(14);

		//Test Search
		returnedView = controller.catalog("1", "", false, model);
		object = (Iterable<Object>) model.asMap().get("catalog");
		assertThat(object).hasSize(2);

		returnedView = controller.catalog("1", "", true, model);
		object = (Iterable<Object>) model.asMap().get("catalog");
		assertThat(object).hasSize(1);

		//Test tags working
		returnedView = controller.catalog("", "durchfall", false, model);
		object = (Iterable<Object>) model.asMap().get("catalog");
		assertThat(object).hasSize(2);

		returnedView = controller.catalog("", "durchfall", true, model);
		object = (Iterable<Object>) model.asMap().get("catalog");
		assertThat(object).hasSize(1);

		returnedView = controller.catalog("900", "durchfall", false, model);
		object = (Iterable<Object>) model.asMap().get("catalog");
		assertThat(object).hasSize(1);

	}

	@Test
	public void searchFormTest() {

		SearchForm form = new SearchForm();

	}
}
