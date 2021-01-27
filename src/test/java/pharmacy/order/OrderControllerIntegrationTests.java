package pharmacy.order;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.salespointframework.order.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import pharmacy.AbstractIntegrationTests;

class OrderControllerIntegrationTests extends AbstractIntegrationTests {

	@Autowired OrderController controller;

	@Test
	@SuppressWarnings("unchecked")
	public void controllerIntegrationTest() {

		Model model = new ExtendedModelMap();

		String returnedView = controller.basket(new Cart(), model);

		assertThat(returnedView).isEqualTo("cart");

	}
}
