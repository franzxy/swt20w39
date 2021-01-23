package pharmacy.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;

class UserManagementUnitTests {


	@Test
	void createsUserAccountWhenCreatingACustomer() {

		// Given
		// … a CustomerRepository returning customers handed into save(…),
		UserRepository repository = mock(UserRepository.class);
		when(repository.save(any())).then(i -> i.getArgument(0));

		// … a UserAccountManager
		UserAccountManagement userAccountManager = mock(UserAccountManagement.class);
		UserAccount userAccount = mock(UserAccount.class);
		when(userAccountManager.create(any(), any(), any())).thenReturn(userAccount);

		// … and the CustomerManagement using both of them,
		UserManagement userManagement = new UserManagement(repository, userAccountManager);

		// When
		// … a registration form is submitted
		UserForm form = new UserForm("name", "password", "address");
		User user = userManagement.addUser(form);

		// Then
		// … a user account creation has been triggered with the proper data and role
		/*verify(userAccountManager, times(1)) //
				.create(eq(form.getName()), //
						eq(UnencryptedPassword.of(form.getPassword())), //
						eq(UserManagement.CUSTOMER_ROLE));
		*/
		// … the customer has a user account attached
		assertThat(user.getUserAccount()).isNotNull();

		assertThat(form.getName().equals("name"));
		assertThat(form.getPassword().equals("password"));
		assertThat(form.getConfirmPassword().equals("address"));

	}
}
