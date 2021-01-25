package pharmacy.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountUnitTests extends AbstractIntegrationTests {

	private BankAccount b;

	@BeforeEach
	public void setUp() {
		b = new BankAccount("name", "iban", "bic");
	}

	@Test
	public void testBankAccount() {
		assertEquals(b.getName(), "name");
		assertEquals(b.getIban(), "iban");
		assertEquals(b.getBic(), "bic");
	}

	@Test
	public  void testChangeBankAccountValues() {
		b.setName("newName");
		b.setIban("newIban");
		b.setBic("newBic");

		assertEquals(b.getName(), "newName");
		assertEquals(b.getIban(), "newIban");
		assertEquals(b.getBic(), "newBic");
	}
}