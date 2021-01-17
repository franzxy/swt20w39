package pharmacy.catalog;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.moduliths.test.ModuleTest;
import org.moduliths.test.ModuleTest.BootstrapMode;
import org.salespointframework.accountancy.Accountancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import pharmacy.Pharmacy;


//@ModuleTest(mode = BootstrapMode.DIRECT_DEPENDENCIES) //Das hier müsste vllt entkommentiert werden
class CatalogModuleTests {


	@Autowired Pharmacy pharmacy;
	@Autowired ConfigurableApplicationContext context;

	@Test
	void verifiesModuleBootstrapped() {

		AssertableApplicationContext assertable = AssertableApplicationContext.get(() -> context);

		//assertThat(assertable).doesNotHaveBean(Accountancy.class);
	}
}
