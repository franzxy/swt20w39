package pharmacy.time;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

class DurationForm {
	
	private final Long duration;

	public DurationForm(Long duration) {
		this.duration = duration;
	}

	public Long getDuration() {
		return duration;
	}
}
