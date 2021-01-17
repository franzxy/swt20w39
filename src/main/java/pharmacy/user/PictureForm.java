package pharmacy.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

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
