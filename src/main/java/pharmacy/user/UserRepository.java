package pharmacy.user;

import org.salespointframework.useraccount.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

interface UserRepository extends CrudRepository<User, Long> {

	@Override
	Streamable<User> findAll();
}
