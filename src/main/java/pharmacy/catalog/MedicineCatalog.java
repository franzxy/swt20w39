package pharmacy.catalog;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;
import pharmacy.catalog.Medicine.PrescriptionType;

public interface MedicineCatalog extends Catalog<Medicine> {
	static final Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();

	Iterable<Medicine> findByPresType(PrescriptionType type, Sort sort);

	default Iterable<Medicine> findByPresType(PrescriptionType type) {
		return findByPresType(type, DEFAULT_SORT);
	}
	
}