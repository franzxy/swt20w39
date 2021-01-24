package pharmacy.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayDirektUnitTests extends AbstractIntegrationTests {

	private PayDirekt p;

	@BeforeEach
	public void setUp() {
		p = new PayDirekt("name");
	}

	@Test
	public void testPayDirekt() {
		assertEquals(p.getName(), "name");
	}

	@Test
	public void testChangePayDirektValues() {
		p.setName("newName");

		assertEquals(p.getName(), "newName");
	}
}
