package pharmacy.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Override
	Streamable<Customer> findAll();
}
