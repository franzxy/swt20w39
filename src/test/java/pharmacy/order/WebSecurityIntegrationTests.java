package pharmacy.order;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityIntegrationTests {

	@Autowired MockMvc mvc;

	/**
	 * Trying to access a secured resource should result in a redirect to the login page.
	 *
	 * @see #19

	@Test
	void redirectsToLoginPageForSecuredResource() throws Exception {

		mvc.perform(get("/orders")) //
				.andExpect(status().isFound()) //
				.andExpect(header().string("Location", endsWith("/login")));
	}

	/**
	 * Trying to access the orders as boss should result in the page being rendered.
	 * 
	 * @see #35

	@Test
	@WithMockUser(username = "boss", roles = "BOSS")
	void returnsModelAndViewForSecuredUriAfterAuthentication() throws Exception {

		mvc.perform(get("/orders")) //
				.andExpect(status().isOk()) //
				.andExpect(view().name("orders")) //
				.andExpect(model().attributeExists("ordersCompleted"));
	}
	*/
}
