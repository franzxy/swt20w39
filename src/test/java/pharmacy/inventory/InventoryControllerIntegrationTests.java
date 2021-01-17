package pharmacy.inventory;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerIntegrationTests {

	@Autowired MockMvc mvc;

	@Test
	void preventPublicAccessForStockOverview() throws Exception {

		mvc.perform(get("/inventory")) //
				.andExpect(status().isFound()) //
				.andExpect(header().string(HttpHeaders.LOCATION, endsWith("/login")));//
	}

	@Test
	@WithMockUser(username = "apo", roles = "BOSS")
	void stockIsAccessibleForAdmin() throws Exception {

		mvc.perform(get("/inventory")) //
				.andExpect(status().isOk()) //
				.andExpect(model().attributeExists("inventory"));
	}
}
