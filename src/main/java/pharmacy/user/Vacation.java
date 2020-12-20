package pharmacy.user;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Vacation {

	private Date startDate;
	private Date endDate;
	private Long duration;
	private Boolean approved;

	public Vacation(Date startDate, Date endDate) {
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = TimeUnit.DAYS.convert(Math.abs(endDate.getTime() - startDate.getTime()), TimeUnit.MICROSECONDS);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date date) {
		startDate = date;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date date) {
		endDate = date;
	}

	public Long getDuration() {
		return duration;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean newApproved) {
		approved = newApproved;
	}
}
