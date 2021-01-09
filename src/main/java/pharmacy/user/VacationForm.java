package pharmacy.user;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

class VacationForm {
	
	@DateTimeFormat
	private final Date startDate;

	@DateTimeFormat
	private final Date endDate;

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
