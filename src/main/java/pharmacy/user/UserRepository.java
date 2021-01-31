package pharmacy.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

/**
 * Repository Instanz für Benutzer
 * @author Timon Trettin
 */
interface UserRepository extends CrudRepository<User, Long> {

	@Override
	Streamable<User> findAll();
}
