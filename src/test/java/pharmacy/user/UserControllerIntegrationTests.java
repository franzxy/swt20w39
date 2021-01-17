package pharmacy.user;

import static org.assertj.core.api.Assertions.*;

import pharmacy.AbstractIntegrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;

class UserControllerIntegrationTests extends AbstractIntegrationTests {

	@Autowired UserController controller;


	//Does not use any authentication and should raise a security exception.
	@Test
	void rejectsUnauthenticatedAccessToController() {

		assertThatExceptionOfType(AuthenticationException.class) //
				.isThrownBy(() -> controller.users(new ExtendedModelMap(), null, null, null));
	}


	//Uses {@link WithMockUser} to simulate access by a user with boss role.
	@Test
	@WithMockUser(roles = "BOSS")
	void allowsAuthenticatedAccessToController() {

		ExtendedModelMap model = new ExtendedModelMap();

		controller.users(model, null, null, null);

		assertThat(model.get("customer")).isNotNull();
	}
}
