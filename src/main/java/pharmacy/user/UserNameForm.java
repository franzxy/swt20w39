package pharmacy.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

class UserNameForm {
	
	@NotEmpty(message = "Benutzername fehlt")
	@ValidUserName(message = "Benutzername wird schon verwendet")
	private final String name;

	public UserNameForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
