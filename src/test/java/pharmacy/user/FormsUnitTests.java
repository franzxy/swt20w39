package pharmacy.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import pharmacy.AbstractIntegrationTests;

import java.util.Date;

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
		EmployeeForm e = new EmployeeForm("salary");

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

	@Test
	public void testPictureForm() {
		PictureForm p = new PictureForm("pic");

		assertEquals(p.getPicture(), "pic");
	}

	@Test
	public void testUserNameForm() {
		UserNameForm u = new UserNameForm("name");

		assertEquals(u.getName(), "name");
	}

	@Test
	public void testVacationForm() {
		String start = "24-12-2000";

		String end = "25-12-2000";

		VacationForm v = new VacationForm(start, end);

		assertEquals(v.getStartDate(), start);
		assertEquals(v.getEndDate(), end);
	}

	@Test
	public void testBankAccountForm() {
		BankAccountForm b = new BankAccountForm("name", "iban", "bic");

		assertEquals(b.getName(), "name");
		assertEquals(b.getIban(), "iban");
		assertEquals(b.getBic(), "bic");
	}

	@Test
	public void testPayDirektForm() {
		PayDirektForm p = new PayDirektForm("name");

		assertEquals(p.getName(), "name");
	}

	@Test
	public void testPaymentCardForm() {
		PaymentCardForm p = new PaymentCardForm("name", "number", "secure");

		assertEquals(p.getName(), "name");
		assertEquals(p.getNumber(), "number");
		assertEquals(p.getSecure(), "secure");
	}
}
