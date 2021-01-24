package pharmacy.user;

import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormsUnitTests extends AbstractIntegrationTests {

	@Test
	public void testAddressForm() {
		AddressForm a = new AddressForm("name", "street", "12345", "city");

		assertEquals(a.getName(), "name");
		assertEquals(a.getStreet(), "street");
		assertEquals(a.getPostCode(), "12345");
		assertEquals(a.getCity(), "city");
	}

	@Test
	public void testEmployeeForm() {
		EmployeeForm e = new EmployeeForm("iban", "salary");

		assertEquals(e.getIban(), "iban");
		assertEquals(e.getSalary(), "salary");
	}

	@Test
	public void testInsuranceForm() {
		InsuranceForm i = new InsuranceForm("company", "insuranceNumber");

		assertEquals(i.getCompany(), "company");
		assertEquals(i.getInsuranceNumber(), "insuranceNumber");
	}

	@Test
	public void testPasswordForm() {
		PasswordForm p = new PasswordForm("password", "confirm");

		assertEquals(p.getNewPassword(), "password");
		assertEquals(p.getConfirmPassword(), "confirm");
	}
}
