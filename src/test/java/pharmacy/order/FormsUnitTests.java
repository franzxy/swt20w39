package pharmacy.order;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;
import pharmacy.finances.FilterForm;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormsUnitTests extends AbstractIntegrationTests {

	@Test
	public void testAddressForm() {
		AddressForm a = new AddressForm("name", "street", "12345", "city");

		assertEquals(a.getName(), "name");
		assertEquals(a.getStreet(), "street");
		assertEquals(a.getPostCode(), "12345");
		assertEquals(a.getCity(), "city");
	}

	@Test
	public void testInsuranceForm() {
		InsuranceForm i = new InsuranceForm("company", "number");

		assertEquals(i.getCompany(), "company");
		assertEquals(i.getInsuranceNumber(), "number");
	}

	@Test
	public void testFilter() {
		OrderFilter f = new OrderFilter();

		assertEquals(f.getFilter(), OrderFilter.Filter.ALLE);
	}

	@Test
	public void testCustomFilter() {
		OrderFilter f = new OrderFilter();

		f.setFilter(OrderFilter.Filter.BEZAHLT);

		assertEquals(f.getFilter(), OrderFilter.Filter.BEZAHLT);
	}

	@Test
	public void testFilterFilter() {
		OrderFilter.Filter filter = OrderFilter.Filter.COMPLETED;

		assertEquals(filter.toString(), "Abgeschlossen");
	}

	@Test
	public void testCheckoutForm() {
		CheckoutForm c = new CheckoutForm(true, "pay", false);

		assertEquals(c.getAddress(), true);
		assertEquals(c.getPayment(), "pay");
		assertEquals(c.getInsurance(), false);
	}
}
