package pharmacy.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharmacy.AbstractIntegrationTests;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VacationUnitTests extends AbstractIntegrationTests {

	private Vacation v;
	private Date start, end;

	@BeforeEach
	public void setUp() {
		start = new Date(10);
		end = new Date(20);
		v = new Vacation(start, end);
	}

	@Test
	public void testVacation() {
		assertEquals(v.getStartDate(), start);
		assertEquals(v.getEndDate(), end);
		assertEquals(v.getDuration(), toIntExact(
				TimeUnit.DAYS.convert(Math.abs(end.getTime() - start.getTime()), TimeUnit.MICROSECONDS)));
		assertEquals(v.getApproved(), null);
	}

	@Test
	public void testChangeVacationValues() {
		Date newStart = new Date(30);
		Date newEnd = new Date(40);

		v.setStartDate(newStart);
		v.setEndDate(newEnd);
		v.setApproved(true);

		assertEquals(v.getStartDate(), newStart);
		assertEquals(v.getEndDate(), newEnd);
		assertEquals(v.getDuration(), toIntExact(
				TimeUnit.DAYS.convert(Math.abs(newEnd.getTime() - newStart.getTime()), TimeUnit.MICROSECONDS)));
		assertEquals(v.getApproved(), true);
	}

	@Test
	public void testVacationEmptyConstructor() {
		Vacation vac = new Vacation();

		assertEquals(vac.getStartDate(), null);
		assertEquals(vac.getEndDate(), null);
		assertEquals(vac.getDuration(), null);
		assertEquals(vac.getApproved(), null);
	}
}
