package pharmacy.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

class CustomerForm {

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String name;

	@NotEmpty(message = "{RegistrationForm.name.NotEmpty}")
	private final String lastName;

	@NotEmpty(message = "{RegistrationForm.email.NotEmpty}")
	private final String email;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}")
	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String password;

	@NotEmpty(message = "{PasswordForm.newPassword.NotEmpty}")
	@Size(min = 8, max = 128, message = "{PasswordForm.newPassword.Size}")
	@Pattern(regexp="^(?=.*[a-z]).+$", message = "{PasswordForm.newPassword.Lower}")
	@Pattern(regexp="^(?=.*[A-Z]).+$", message = "{PasswordForm.newPassword.Upper}")
	@Pattern(regexp="^(?=.*[-+_!@#$%^&*.,?]).+$", message = "{PasswordForm.newPassword.Special}")
	@Pattern(regexp="^[\\S]+$", message = "{PasswordForm.newPassword.Space}")
	private final String confirmPassword;

	@AssertTrue(message="Old password incorrect")
	private boolean confirmValid() {
		return this.password.equals(confirmPassword);
	}
	
	@NotEmpty(message = "{DeliveryForm.name.NotEmpty}")
	private final String street;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String houseNumber;

	private final Long postCode;

	@NotEmpty(message = "{DeliveryForm.email.NotEmpty}")
	private final String city;
	
	private final Boolean privateInsurance;

	public CustomerForm(String name, String lastName, String email, String password, String confirmPassword, String street, String houseNumber, Long postCode, String city, Boolean privateInsurance) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
		this.privateInsurance = privateInsurance;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public Long getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}

	public Boolean getPrivateInsurance() {
		return privateInsurance;
	}
}
