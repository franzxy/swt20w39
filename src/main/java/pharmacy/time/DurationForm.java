package pharmacy.time;
/**
 * Formular zum überspringen der Zeit.
 * @author Timon Trettin
 */
class DurationForm {
	
	private final Long duration;

    /**
     * Initialisiert die zu überspringende Zeit in Stunden.
     */
	public DurationForm(Long duration) {
		this.duration = duration;
	}

	public Long getDuration() {
		return duration;
	}
}
