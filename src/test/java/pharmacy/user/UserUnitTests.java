package pharmacy.user;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTests extends AbstractIntegrationTests {

	private User u;

	@BeforeEach
	public void setUp() {
		u = new User();
	}

	@Test
	public void testUser() {
		assertNull(u.getUserAccount());
		assertNull(u.getPayDirekt());
		assertNull(u.getBankAccount());
		assertNull(u.getPaymentCard());
		assertNull(u.getAddress());
		assertNull(u.getPicture());
		assertNull(u.getOrdered());
		assertNull(u.getInsurance());
		assertNull(u.getSalary());
		assertNull(u.getVacationRemaining());
		assertEquals(u.getVacations(), new ArrayList<Vacation>());

	}

	@Test
	public void testChangeUserValues() {
		Address a = new Address();
		Insurance i = new Insurance();

		u.setPayDirekt(new PayDirekt(""));
		u.setBankAccount(new BankAccount("", "", ""));
		u.setPaymentCard(new PaymentCard("", "", ""));
		u.changeAddress(a);
		u.setPicture("");
		u.setOrdered(true);
		u.setInsurance(i);
		u.setSalary(Money.of(1, "EUR"));
		u.setVacationRemaining(1);

		assertEquals(u.getPayDirekt(), new PayDirekt(""));
		assertEquals(u.getBankAccount(), new BankAccount("", "", ""));
		assertEquals(u.getPaymentCard(), new PaymentCard("", "", ""));
		assertEquals(u.getAddress(), a);
		assertEquals(u.getPicture(), "");
		assertEquals(u.getOrdered(), true);
		assertEquals(u.getInsurance(), i);
		assertEquals(u.getSalary(), Money.of(1, "EUR"));
		assertEquals(u.getVacationRemaining(), 1);

		u.addVacation(new Vacation());
		assertEquals(u.getVacations().size(), 1);
		u.removeVacation(0);
		assertEquals(u.getVacations().size(), 0);
	}
}