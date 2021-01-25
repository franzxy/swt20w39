package pharmacy.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsuranceUnitTests extends AbstractIntegrationTests {

	private Insurance i;

	@BeforeEach
	public void setUp() {
		i = new Insurance("company", "number");
	}

	@Test
	public void testInsurance() {
		assertEquals(i.getCompany(), "company");
		assertEquals(i.getId(), "number");
	}

	@Test
	public void testChangeInsuranceValues() {
		i.setCompany("newCompany");
		i.setId("newId");

		assertEquals(i.getCompany(), "newCompany");
		assertEquals(i.getId(), "newId");
	}

	@Test
	public void testInsuranceEmptyConstructor() {
		Insurance ii = new Insurance();

		assertEquals(ii.getId(), null);
		assertEquals(ii.getCompany(), null);
	}
}
