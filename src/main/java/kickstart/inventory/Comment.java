package kickstart.inventory;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// (｡◕‿◕｡)
// Eigene Entity Klasse um Kommentare für Discs zu speichern
// Alle JPA Anforderungen erfüllt :)
// Mit der Table-Annotation kann man u.a. den Name der Tabelle angeben, ansonsten wird der Klassennamen genommen

@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

	private static final long serialVersionUID = -7114101035786254953L;

	// (｡◕‿◕｡)
	// Falls man die Id nicht selber setzen will, kann die mit @GeneratedValue vom JPA-Provider generiert und gesetzt
	// werden
	private @Id @GeneratedValue long id;

	private String text;
	private int rating;

	private LocalDateTime date;

	@SuppressWarnings("unused")
	private Comment() {}

	public Comment(String text, int rating, LocalDateTime dateTime) {

		this.text = text;
		this.rating = rating;
		this.date = dateTime;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getRating() {
		return rating;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
