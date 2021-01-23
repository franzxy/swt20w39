package pharmacy.catalog;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MedicineUnitTests extends AbstractIntegrationTests {

	@Test
	void testMedicineClass() {

		List<String> list = new ArrayList<>();
		list.add("category");

		Medicine m = new Medicine("name", "description",
				Money.of(12, "EUR"), Money.of(12, "EUR"),
				list, 1.0, true, "", 1);

		assertThat(m.getName().equals("name"));
		assertThat(m.getDescription().equals("description"));
		assertThat(m.getCategories().equals(list));
		assertThat(m.getAmount() == 1.0);
		assertThat(m.isPresonly() == true);
		assertThat(m.getQuantity() == 1);



	}
}
