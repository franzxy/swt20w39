package pharmacy.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

interface DoctorRepository extends CrudRepository<Doctor, Long> {

	@Override
	Streamable<Doctor> findAll();
}
