package pharmacy.user;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import org.salespointframework.time.BusinessTime;

class VacationForm {

	@NotEmpty(message = "Start fehlt")
	@FutureOrPresent(message = "Darf nicht vor Heute liegen")
	private final Date startDate;

	@NotEmpty(message = "Ende fehlt")
	@Future(message = "Darf nicht vor Morgen liegen")
	private final Date endDate;
/*
	public VacationForm(String startDate, String endDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.startDate = formatter.parse(startDate);
		this.endDate = formatter.parse(endDate);
	}
*/
	public VacationForm(Date startDate, Date endDate) {

		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
}
