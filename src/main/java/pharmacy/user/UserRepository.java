package pharmacy.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

interface UserRepository extends CrudRepository<User, Long> {

	@Override
	Streamable<User> findAll();
}
