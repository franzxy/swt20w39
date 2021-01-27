package pharmacy.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentCardUnitTests extends AbstractIntegrationTests {

	private PaymentCard p;

	@BeforeEach
	public void setUp() {
		p = new PaymentCard("name", "number", "secure");
	}

	@Test
	public void testPaymentCard() {
		assertEquals(p.getName(), "name");
		assertEquals(p.getNumber(), "number");
		assertEquals(p.getSecure(), "secure");
	}

	@Test
	public void testChangePaymentCardValues() {
		p.setName("newName");
		p.setNumber("newNumber");
		p.setSecure("newSecure");

		assertEquals(p.getName(), "newName");
		assertEquals(p.getNumber(), "newNumber");
		assertEquals(p.getSecure(), "newSecure");
	}
}
