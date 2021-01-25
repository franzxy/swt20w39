package pharmacy.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressUnitTests extends AbstractIntegrationTests {

	private Address a;

	@Test
	public void testAddress() {
		a = new Address("name", "street", "12345", "city");

		assertEquals(a.getName(), "name");
		assertEquals(a.getStreet(), "street");
		assertEquals(a.getPostCode(), "12345");
		assertEquals(a.getCity(), "city");
	}

	@Test
	public void testEmptyAddressConstructor() {
		a = new Address();

		assertEquals(a.getName(), null);
		assertEquals(a.getStreet(), null);
		assertEquals(a.getPostCode(), null);
		assertEquals(a.getCity(), null);
	}


	@Test
	public void testChangeAddressValues() {
		a.setName("newName");
		a.setCity("newCity");
		a.setPostCode("67890");
		a.setStreet("newStreet");

		assertEquals(a.getName(), "newName");
		assertEquals(a.getStreet(), "newStreet");
		assertEquals(a.getPostCode(), "67890");
		assertEquals(a.getCity(), "newCity");
	}
}