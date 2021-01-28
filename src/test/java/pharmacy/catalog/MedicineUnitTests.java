package pharmacy.catalog;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MedicineUnitTests extends AbstractIntegrationTests {

	private Medicine m;
	private List list;

	@BeforeEach
	public void setUp() {
		list = new ArrayList<>();
		list.add("category");

		m = new Medicine("name", "description",
				Money.of(12, "EUR"), Money.of(13, "EUR"),
				list, 1.0, true, "img", 1);
	}

	@Test
	public void testMedicineClass() {
		assertThat(m.getName().equals("name"));
		assertThat(m.getDescription().equals("description"));
		assertThat(m.getCategories().toList().equals(list));
		assertThat(m.getAmount() == 1.0);
		assertThat(m.isPresonly());
		assertThat(m.getQuantity() == 1);
		assertThat(m.getImage().equals("img"));
		assertThat(m.getPrice().equals(Money.of(12, "EUR")));
		assertThat(m.getPurchaseprice().equals(Money.of(13, "EUR")));

		assertThat(!m.hasImage());
	}

	@Test
	public void testChangeMedicineValues() {
		m.setDescription("newDesc");
		m.setPurchaseprice(Money.of(5, "EUR"));
		m.setImage("newImg");
		m.setAmount(2.0);
		m.setQuantity(3);

		assertThat(m.getDescription().equals("newDesc"));
		assertThat(m.getPurchaseprice().equals(Money.of(5, "EUR")));
		assertThat(m.getImage().equals("newImg"));
		assertThat(m.getAmount() == 2.0);
		assertThat(m.getQuantity() == 3);
	}
}