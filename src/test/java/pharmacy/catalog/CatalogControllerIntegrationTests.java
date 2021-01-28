package pharmacy.catalog;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.time.BusinessTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import pharmacy.AbstractIntegrationTests;

import java.util.ArrayList;
import java.util.List;

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

		String header = (String)model.asMap().get("header");
		assertEquals(header, "Ergebnisse für \"900\" in Kategorie Durchfall:");

	}

	@Test
	public void searchFormTest() {

		SearchForm form = new SearchForm();
		form.setSearchTerm("Term");
		form.setNoPres(true);
		form.setTag("Tag");

		assertThat(form.getSearchTerm().equals("Term"));
		assertThat(form.getTag().equals("Tag"));
		assertThat(form.getNoPres() == true);

	}

	@Test
	public void submitSearchTest() {
		SearchForm form = new SearchForm();
		form.setSearchTerm("term");
		form.setTag("tag");
		form.setNoPres(true);

		Model model = new ExtendedModelMap();

		String result = controller.submitSearchInCatalog("term", form, model);

		assertEquals(result, "redirect:/?s=term&p=true&t=tag");

	}

	@Test
	public void testDetailPage() {
		Model model = new ExtendedModelMap();

		List list = new ArrayList<>();
		list.add("category");

		Medicine m = new Medicine("name", "description",
				Money.of(12, "EUR"), Money.of(13, "EUR"),
				list, 1.0, true, "img", 1);

		String returnedView = controller.detail(m, model);

		assertThat(returnedView).isEqualTo("detail");

		Medicine returnedMed = (Medicine) model.asMap().get("medicine");

		assertEquals(returnedMed.getName(), "name");
	}
}
