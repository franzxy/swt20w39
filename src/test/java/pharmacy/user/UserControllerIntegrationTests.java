package pharmacy.user;

import static org.assertj.core.api.Assertions.*;

import pharmacy.AbstractIntegrationTests;
import pharmacy.inventory.ErrorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

class UserControllerIntegrationTests extends AbstractIntegrationTests {

	@Autowired UserController controller;

	@Autowired
	private UserManagement userManagement;

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
	@Test
	void registerTest(){
		Model model = new ExtendedModelMap();

		UserForm f = new UserForm("muster", "1234", "123");

		String res = this.controller.register(model,f);
		assertEquals(res, "register");
		
		assertEquals(f, model.asMap().get("userForm"));
		
	}
	@Test
	void changeRegisterTest(){
		Errors e = new ErrorTest();
		UserForm f = new UserForm("test", "123", "123");
		String res = this.controller.changeRegister(f,e);
		assertEquals(res, "redirect:/login");
		boolean exists = this.userManagement.findAll().filter(u ->{
			return u.getUserAccount().getUsername().equals("test");
		}).get().findFirst().isPresent();
		assertTrue(exists);
		e.addAllErrors(null);
		res = this.controller.changeRegister(f,e);
		assertEquals(res, "register");
	
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void changeInsuranceTest(){
		long uid = this.userManagement.findAll().stream().findFirst().get().getId();
		InsuranceForm f = new InsuranceForm("cass", "number");
		Errors e = new ErrorTest();
		String res = this.controller.changeInsurance(uid, f, e);
		assertEquals(res, "redirect:/users");
		assertEquals(this.userManagement.findUser(uid).get().getInsurance().getCompany(), "cass");
		assertEquals(this.userManagement.findUser(uid).get().getInsurance().getId(), "number");
		e.addAllErrors(null);
		res = this.controller.changeInsurance(uid, f, e);
		assertEquals(res, "redirect:/users");
	}
	
}
