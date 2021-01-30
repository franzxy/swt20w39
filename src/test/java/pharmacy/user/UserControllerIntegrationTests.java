package pharmacy.user;

import static org.assertj.core.api.Assertions.*;

import pharmacy.AbstractIntegrationTests;
import pharmacy.inventory.ErrorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.useraccount.Role;
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
	@Test
	@WithMockUser(roles = "BOSS")
	void addAddressTest(){
		long uid = this.userManagement.findAll().stream().findFirst().get().getId();
		AddressForm f = new AddressForm("example", "this way", "12324", "NYX");
		Errors e = new ErrorTest();
		String res = this.controller.addAddress(uid, f, e);
		assertEquals(res, "redirect:/users");
		assertEquals(this.userManagement.findUser(uid).get().getAddress().getCity(), "NYX");
		assertEquals(this.userManagement.findUser(uid).get().getAddress().getPostCode(), "12324");
		assertEquals(this.userManagement.findUser(uid).get().getAddress().getName(), "example");
		assertEquals(this.userManagement.findUser(uid).get().getAddress().getStreet(), "this way");
		e.addAllErrors(null);
		res = this.controller.addAddress(uid, f, e);
		assertEquals(res, "redirect:/users");
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void hireEmployeeTest(){
		long uid = this.userManagement.findAll().stream().filter(u->u.getUserAccount().hasRole(Role.of("CUSTOMER")))
			.findFirst().get().getId();
		String res = this.controller.hireEmployee(uid);
		assertEquals(res, "redirect:/users");
		assertTrue(this.userManagement.findUser(uid).get().getUserAccount().hasRole(Role.of("EMPLOYEE")));
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void dismissEmployeeTest(){
		long uid = this.userManagement.findAll().stream().filter(u->u.getUserAccount().hasRole(Role.of("EMPLOYEE")))
			.findFirst().get().getId();
		String res = this.controller.dismissEmployee(uid);
		assertEquals(res, "redirect:/users");
		assertFalse(this.userManagement.findUser(uid).get().getUserAccount().hasRole(Role.of("EMPLOYEE")));
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void changeEmployeeSalaryTest(){
		long uid = this.userManagement.findAll().stream().filter(u->u.getUserAccount().hasRole(Role.of("EMPLOYEE")))
			.findFirst().get().getId();
		EmployeeForm f = new EmployeeForm("2000");
		Errors e = new ErrorTest();

		String res = this.controller.changeEmployeeSalary(uid,f,e);
		assertEquals(res, "redirect:/users");
		e.addAllErrors(null);
		res = this.controller.changeEmployeeSalary(uid,f,e);
		assertEquals(res, "redirect:/users");
		assertTrue(this.userManagement.findUser(uid).get().getSalary().isEqualTo(Money.of(2000, "EUR")));
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void removeUserTest(){
		long uid = this.userManagement.findAll().stream().filter(u->u.getUserAccount().hasRole(Role.of("CUSTOMER")))
			.findFirst().get().getId();
		String res = this.controller.removeUser(uid);
		assertEquals(res, "redirect:/users");
		assertFalse(this.userManagement.findUser(uid).isPresent());
	}
	/** 
	//TODO: x is not found, add vac. first
	@Test
	@WithMockUser(roles = "BOSS")
	void approveVacationsTest(){
		long uid = this.userManagement.findAll().stream().filter(u->u.getUserAccount().hasRole(Role.of("EMPLOYEE")))
			.findFirst().get().getId();
		String res = this.controller.approveVacations(uid, 0);
		assertEquals(res, "redirect:/users");
	}
	//TODO: x is not found, add vac. first
	@Test
	@WithMockUser(roles = "BOSS")
	void rejectVacationsTest(){
		long uid = this.userManagement.findAll().stream().filter(u->u.getUserAccount().hasRole(Role.of("EMPLOYEE")))
			.findFirst().get().getId();
		String res = this.controller.rejectVacations(uid, 0);
		assertEquals(res, "redirect:/users");
	}
	*/
	@Test
	@WithMockUser(roles = "BOSS", username="apo")
	void accountTest(){
		Model model = new ExtendedModelMap();
		PictureForm f = new PictureForm("picture");
		String res = this.controller.account(model, f);
		assertEquals(res, "account");
		assertEquals(model.asMap().get("user"),this.userManagement.currentUser().get());
		assertEquals(model.asMap().get("customer"), Role.of("CUSTOMER"));
		assertEquals(model.asMap().get("employee"), Role.of("EMPLOYEE"));
		assertEquals(model.asMap().get("boss"), Role.of("BOSS"));
		assertEquals(model.asMap().get("pictureForm"), f);
	}
	@Test
	@WithMockUser(roles = "EMPLOYEE", username = "hans")
	void changePictureTest(){
		Model model = new ExtendedModelMap();
		PictureForm f = new PictureForm("picture");
		Errors e = new ErrorTest();
		String res = this.controller.changePicture(model, f, e);
		assertEquals(res, "redirect:/account");
		assertEquals(model.asMap().get("user"), userManagement.currentUser().get());
		assertEquals(this.userManagement.currentUser().get().getPicture(), "picture");
		e.addAllErrors(null);
		res = this.controller.changePicture(model, f, e);
		assertEquals(res, "settings");

	}
	@Test
	@WithMockUser(roles = "EMPLOYEE", username = "hans")
	void passwordTest(){
		Model model = new ExtendedModelMap();
		PasswordForm f = new PasswordForm("1234", "1234");
		String res = this.controller.password(model,f);
		assertEquals(res, "password");
		assertEquals(model.asMap().get("user"), userManagement.currentUser().get());
		assertEquals(model.asMap().get("customer"), Role.of("CUSTOMER"));
		assertEquals(model.asMap().get("employee"), Role.of("EMPLOYEE"));
		assertEquals(model.asMap().get("boss"), Role.of("BOSS"));
		assertEquals(model.asMap().get("passwordForm"), f);
	}
	@Test
	@WithMockUser(roles = "EMPLOYEE", username = "hans")
	void insuranceTest(){
		Model model = new ExtendedModelMap();
		InsuranceForm f = new InsuranceForm("cass", "abc");
		String res = this.controller.insurance(model,f);
		assertEquals(res, "insurance");
		assertEquals(model.asMap().get("user"), userManagement.currentUser().get());
		assertEquals(model.asMap().get("customer"), Role.of("CUSTOMER"));
		assertEquals(model.asMap().get("employee"), Role.of("EMPLOYEE"));
		assertEquals(model.asMap().get("boss"), Role.of("BOSS"));
		assertEquals(model.asMap().get("insuranceForm"), f);

	}
	@Test
	@WithMockUser(roles = "EMPLOYEE", username = "hans")
	void account2Test(){
		Model model = new ExtendedModelMap();
		AddressForm f = new AddressForm("example", "this way", "12324", "NYX");
		String res = this.controller.account(model,f);
		assertEquals(res, "address");
		assertEquals(model.asMap().get("user"), userManagement.currentUser().get());
		assertEquals(model.asMap().get("customer"), Role.of("CUSTOMER"));
		assertEquals(model.asMap().get("employee"), Role.of("EMPLOYEE"));
		assertEquals(model.asMap().get("boss"), Role.of("BOSS"));
		assertEquals(model.asMap().get("addressForm"), f);
	}
	@Test
	@WithMockUser(roles = "EMPLOYEE", username = "hans")
	void accountPaymantsTest(){
		Model model = new ExtendedModelMap();
		BankAccountForm f1 = new BankAccountForm("a", "b", "c");
		PaymentCardForm f2 = new PaymentCardForm("hello", "number", "secure");
		PayDirektForm f3 = new PayDirektForm("name");
		String res = this.controller.accountPayments(model,f1,f2,f3);
		assertEquals(res, "payments");
		assertEquals(model.asMap().get("user"), userManagement.currentUser().get());
		assertEquals(model.asMap().get("customer"), Role.of("CUSTOMER"));
		assertEquals(model.asMap().get("employee"), Role.of("EMPLOYEE"));
		assertEquals(model.asMap().get("boss"), Role.of("BOSS"));
		assertEquals(model.asMap().get("bankAccountForm"), f1);
		assertEquals(model.asMap().get("creditCardForm"), f2);
		assertEquals(model.asMap().get("payDirektForm"), f3);
		
	}
	@Test
	@WithMockUser(roles = "EMPLOYEE", username = "hans")
	void changeAccountBankTest(){
		Model model = new ExtendedModelMap();
		BankAccountForm f1 = new BankAccountForm("a", "b", "c");
		PaymentCardForm f2 = new PaymentCardForm("hello", "number", "secure");
		PayDirektForm f3 = new PayDirektForm("name");
		Errors e = new ErrorTest();
		String res = this.controller.changeAccountBank(model,f1,e,f2,f3);
		assertEquals(res, "redirect:/account/payments");
		assertEquals(model.asMap().get("user"), userManagement.currentUser().get());
		assertEquals(model.asMap().get("creditCardForm"), f2);
		assertEquals(model.asMap().get("payDirektForm"), f3);
		e.addAllErrors(null);
		res = this.controller.changeAccountBank(model,f1,e,f2,f3);
		assertEquals(res, "payments");
		assertEquals(this.userManagement.currentUser().get().getBankAccount().getBic(), "c");
		assertEquals(this.userManagement.currentUser().get().getBankAccount().getIban(), "b");
		assertEquals(this.userManagement.currentUser().get().getBankAccount().getName(), "a");
	}




}
