package pharmacy.user;

import javax.validation.constraints.NotEmpty;

/**
 * Formular für PayDirekt
 * @author Timon Trettin
 */
class PayDirektForm {

	@NotEmpty(message = "Name fehlt")
	private final String name;

	public PayDirektForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
