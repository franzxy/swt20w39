package pharmacy.finances;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;
/**
 * Diese Klasse dient zum Filtern der Finanzen.
 * Der IntervallFilter besteht aus 3 Komponenten:
 * 		1. Startdatum -> {@link String}
 * 		2. Enddatum -> {@link String}
 * 		3. Aktiviert -> {@link Boolean}
 * Der Rest wird mithilfe des enums {@link Filter} gefiltert.
 * 
 * @author Lukas Luger
 */

public class FilterForm{
	public enum Filter {
		ALLE("Alles"),
		OBEST("Kunden Bestellungen"),
		GEHAELTER("Gehälter"),
		STROM("Strom"),
		MIETE("Miete"),
		WASSER("Wasser"),
		HEIZ("Heizkosten");
		
		@Id
		private int id;
		
		private final String s;
		/**
		 * Setzt den Filter mithilfe eines Strings.
		 * @param es
		 */
		Filter(String es){

			this.s = es;

		}
		/**
		 * Gibt den String zu dem gesetzten Filter zurück.
		 * @return String s
		 */
		@Override
		public String toString() {

			return s;

		}
		/**
		 * Setzt die id des Objekts.
		 * @param id
		 */
		public void setId(int id){

			Assert.notNull(id, "Id must not be null!");

			this.id=id;

		}

	}
	
	private Filter filter;

	private String begin;

	private String end;

	private boolean intfilter;

	/**
	 * Initialisiert alle Attribute.
	 */
	public FilterForm() {

		this.filter=Filter.ALLE;
		this.end="";
		this.begin="";
		this.intfilter=false;

	}
	/**
	 * Gibt den gesetzten enum {@link Filter} zurück.
	 * @return {@link Filter}
	 */
	public Filter getFilter() {

		return filter;

	}
	/**
	 * Setzt den {@link Filter}.
	 * @param filter
	 */
	public void setFilter(Filter filter) {

		Assert.notNull(filter, "Filter must not be null!");

		this.filter = filter;

	}
	/**
	 * Gibt das Startdatum zurück in Form eines Strings.
	 * @return {@link String} begin
	 */
	public String getBegin() {

		return begin;

	}
	/**
	 * Setzt das {@link String} Sartdatum.
	 * @param begin
	 */
	public void setBegin(String begin) {

		Assert.notNull(begin, "Begin must not be null!");

		this.begin = begin;

	}
	/**
	 * Gibt das Enddatum als String zurück.
	 * @return {@link String} end
	 */
	public String getEnd() {

		return end;

	}
	/**
	 * Setzt das {@link String} Enddatum.
	 * @param end
	 */
	public void setEnd(String end) {

		Assert.notNull(end, "End must not be null!");

		this.end = end;

	}
	/**
	 * Gibt zurück ob der IntervallFilter aktiv ist oder nicht.
	 * 
	 * @return {@link Boolean} intfilter
	 */
	public boolean isIntfilter() {

		return intfilter;

	}
	/**
	 * Legt fest ob nach Intervall gefiltert werden soll mithilfe {@link Boolean}.
	 * @param intfilter
	 */
	public void setIntfilter(boolean intfilter) {

		Assert.notNull(intfilter, "IntFilter must not be null!");

		this.intfilter = intfilter;

	}

}
