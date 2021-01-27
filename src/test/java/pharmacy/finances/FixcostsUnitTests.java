package pharmacy.finances;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;
import static org.junit.jupiter.api.Assertions.*;

public class FixcostsUnitTests extends AbstractIntegrationTests {

	private Fixcosts f;

	@BeforeEach
	public void setUp() {
		f = new Fixcosts();
	}

	@Test
	public void testFixcosts() {
		assertEquals(f.getRent(), 0.0);
		assertEquals(f.getElectricity(), 0);
		assertEquals(f.getWater(), 0);
		assertEquals(f.getHeating(), 0);
	}

	@Test
	public void testChangeFixcostsValues() {
		f.setElectricity(1);
		f.setHeating(2);
		f.setRent(3);
		f.setWater(4);

		assertEquals(f.getElectricity(), 1);
		assertEquals(f.getHeating(), 2);
		assertEquals(f.getRent(), 3);
		assertEquals(f.getWater(), 4);
	}
}