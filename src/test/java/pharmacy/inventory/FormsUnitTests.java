package pharmacy.inventory;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;
import pharmacy.catalog.Medicine;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormsUnitTests extends AbstractIntegrationTests {

	@Test
	public void testMedicineForm() {
		MedicineForm m = new MedicineForm();

		assertEquals(m.getDescription(), "");
		assertEquals(m.getName(), "");
		assertEquals(m.getTags(), "");
		assertEquals(m.getImage(), "");
		assertEquals(m.getAmount(), 0.0);
		assertEquals(m.isPresonly(), false);
		assertEquals(m.getPrice(), 0.0);
		assertEquals(m.getPurchasingprice(), 0.0);
		assertEquals(m.getQuantity(), 1);
		assertEquals(m.getId(), "");
	}

	@Test
	public void testChangeMedicineFormValues() {
		MedicineForm m = new MedicineForm();

		m.setDescription("s");
		m.setName("n");
		m.setTags("t");
		m.setImage("i");
		m.setAmount(1.0);
		m.setPresonly(true);
		m.setPrice(1.0);
		m.setPurchasingprice(1.0);
		m.setQuantity(0);
		m.setId("id");

		assertEquals(m.getDescription(), "s");
		assertEquals(m.getName(), "n");
		assertEquals(m.getTags(), "t");
		assertEquals(m.getImage(), "i");
		assertEquals(m.getAmount(), 1.0);
		assertEquals(m.isPresonly(), true);
		assertEquals(m.getPrice(), 1.0);
		assertEquals(m.getPurchasingprice(), 1.0);
		assertEquals(m.getQuantity(), 0);
		assertEquals(m.getId(), "id");

	}

	@Test
	public void testToMedicine() {
		MedicineForm m = new MedicineForm();

		m.setDescription("s");
		m.setName("n");
		m.setTags("t");
		m.setImage("i");
		m.setAmount(1.0);
		m.setPresonly(true);
		m.setPrice(1.0);
		m.setPurchasingprice(1.0);
		m.setQuantity(0);
		m.setId("id");

		Medicine med = new Medicine("n", "s", Money.of(1.0, "EUR"), Money.of(1.0, "EUR"),
				Arrays.asList("t"), 1.0, true, "1", 0);

		assertEquals(m.toMedicine().getName(), med.getName());
		assertEquals(m.toMedicine().getDescription(), med.getDescription());
	}
}
