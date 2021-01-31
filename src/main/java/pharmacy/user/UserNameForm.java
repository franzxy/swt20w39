package pharmacy.user;

import javax.validation.constraints.NotEmpty;

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
