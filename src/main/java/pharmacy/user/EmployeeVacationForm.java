package pharmacy.user;

import java.util.Date;

class EmployeeVacationForm {
	
	private final Date startDate;

	private final Date endDate;

	public EmployeeVacationForm(Date startDate, Date endDate) {
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
