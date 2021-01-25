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
		Date start = new Date();
		start.setTime(10);

		Date end = new Date();
		end.setTime(20);

		VacationForm v = new VacationForm(start, end);

		assertEquals(v.getStartDate(), start);
		assertEquals(v.getEndDate(), end);
	}
}
