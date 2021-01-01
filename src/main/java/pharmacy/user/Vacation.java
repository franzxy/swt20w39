package pharmacy.user;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VACATIONS")
public class Vacation implements Serializable {

	private @Id @GeneratedValue long id;

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
