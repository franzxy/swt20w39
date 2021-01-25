package pharmacy.time;

import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;
import pharmacy.user.Insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormsUnitTests extends AbstractIntegrationTests {

	@Test
	public void testDurationForm() {
		DurationForm d = new DurationForm((long)1);

		assertEquals(d.getDuration(), 1);
	}
}
