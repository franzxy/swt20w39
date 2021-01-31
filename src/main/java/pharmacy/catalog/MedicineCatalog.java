package pharmacy.catalog;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;

/**
 * Interface zur Interaktion mit dem {@link MedicineCatalog}
 */
public interface MedicineCatalog extends Catalog<Medicine> {
	
	static final Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();

	Iterable<Medicine> findByPresonly(boolean presonly, Sort sort);

	default Iterable<Medicine> findByPresonly(boolean presonly) {
		return findByPresonly(presonly, DEFAULT_SORT);
	}
	
}