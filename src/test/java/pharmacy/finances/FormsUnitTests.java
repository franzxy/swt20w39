package pharmacy.finances;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

public class FormsUnitTests extends AbstractIntegrationTests {

	private FilterForm f;

	@BeforeEach
	public void setUp() {
		f = new FilterForm();
	}

	@Test
	public void testFilterForm() {
		assertEquals(f.getFilter(), FilterForm.Filter.ALLE);
		assertEquals(f.getBegin(), "");
		assertEquals(f.getEnd(), "");
		assertFalse(f.isIntfilter());
	}

	@Test
	public void testChangeFilterFormValues() {
		f.setFilter(FilterForm.Filter.HEIZ);
		f.setBegin("begin");
		f.setEnd("end");
		f.setIntfilter(true);

		assertEquals(f.getFilter(), FilterForm.Filter.HEIZ);
		assertEquals(f.getBegin(), "begin");
		assertEquals(f.getEnd(), "end");
		assertTrue(f.isIntfilter());
	}

	@Test
	public void testFilter() {
		FilterForm.Filter filter = FilterForm.Filter.ALLE;

		assertEquals(filter.toString(), "Alles");
	}
}
