package pharmacy.user;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import org.salespointframework.time.BusinessTime;

class VacationForm {

	private final String startDate;
	private Date startTempDate;

	private final String endDate;
	private Date endTempDate;
	
	public VacationForm(String startDate, String endDate) {

		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date dateRepresentation = cal.getTime();
		startTempDate = dateRepresentation;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
		try {
			startTempDate = formatter.parse(startDate);
		} catch(Exception e) {
		 
		}
		return startTempDate;
	}

	public Date getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date dateRepresentation = cal.getTime();
		endTempDate = dateRepresentation;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
		try {
			endTempDate = formatter.parse(endDate);
		} catch(Exception e) {
		 
		}
		return endTempDate;
	}
}
