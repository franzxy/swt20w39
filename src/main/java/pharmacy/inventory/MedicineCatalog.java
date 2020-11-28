package kickstart.inventory;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;

import kickstart.inventory.Medicine.MedicineType;

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
	Iterable<Medicine> findByType(MedicineType type, Sort sort);

	/**
	 * Returns all {@link Disc}s by type ordered by their identifier.
	 *
	 * @param type must not be {@literal null}.
	 * @return the discs of the given type, never {@literal null}.
	 */
	default Iterable<Medicine> findByType(MedicineType type) {
		return findByType(type, DEFAULT_SORT);
	}
}