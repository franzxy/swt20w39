package pharmacy.finances;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

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
		
		Filter(String es){

			this.s=es;

		}
	
		@Override
		public String toString() {

			return s;

		}

		public void setId(int id){

			Assert.notNull(id, "Id must not be null!");

			this.id=id;

		}

	}
	
	private Filter filter;

	private String begin;

	private String end;

	private boolean intfilter;

	public FilterForm() {

		this.filter=Filter.ALLE;
		this.end="";
		this.begin="";
		this.intfilter=false;

	}
	
	public Filter getFilter() {

		return filter;

	}

	public void setFilter(Filter filter) {

		Assert.notNull(filter, "Filter must not be null!");

		this.filter = filter;

	}

	public String getBegin() {

		return begin;

	}

	public void setBegin(String begin) {

		Assert.notNull(begin, "Begin must not be null!");

		this.begin = begin;

	}

	public String getEnd() {

		return end;

	}

	public void setEnd(String end) {

		Assert.notNull(end, "End must not be null!");

		this.end = end;

	}

	public boolean isIntfilter() {

		return intfilter;

	}

	public void setIntfilter(boolean intfilter) {

		Assert.notNull(intfilter, "IntFilter must not be null!");

		this.intfilter = intfilter;

	}

}
