package pharmacy.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

class PayDirektForm {
	
	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String username;

	public PayDirektForm(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
