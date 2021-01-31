package pharmacy.user;

import javax.validation.constraints.NotEmpty;
/**
 * Formular für Profilbild
 * @author Timon Trettin
 */
class PictureForm {
	
	@NotEmpty(message = "PayDirekt Benutzername fehlt")
	private final String picture;

	public PictureForm(String picture) {
		this.picture = picture;
	}

	public String getPicture() {
		return picture;
	}
}
