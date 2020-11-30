package pharmacy.catalog;

import pharmacy.catalog.Medicine.PrescriptionType;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;

/**
 * An extension of {@link Catalog} to add video shop specific query methods.
 *
 * @author Oliver Gierke
 */
public interface MedicineCatalog extends Catalog<Medicine> {

	static final Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();

	/**
	 * Returns all {@link Disc}s by type ordered by the given {@link Sort}.
	 *
	 * @param type must not be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return the discs of the given type, never {@literal null}.
	 */
	Iterable<Medicine> findByType(PrescriptionType type, Sort sort);

	/**
	 * Returns all {@link Disc}s by type ordered by their identifier.
	 *
	 * @param type must not be {@literal null}.
	 * @return the discs of the given type, never {@literal null}.
	 */
	default Iterable<Medicine> findByType(PrescriptionType type) {
		return findByType(type, DEFAULT_SORT);
	}
}