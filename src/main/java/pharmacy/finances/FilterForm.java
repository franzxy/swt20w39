package pharmacy.finances;

import org.springframework.data.annotation.Id;

public class FilterForm{
	public enum Filter {
		ALLE("Alles"),
		OBEST("Online Bestellungen"),
		GEHÄLTER("Gehälter"),
		STROM("Strom"),
		MIETE("Miete"),
		WASSER("Wasser"),
		HEIZ("Heizkosten");
		
		@Id
		private int id;
		
		
		//@Value("alle")
		private final String s;
		
		Filter(String es){
			this.s=es;
		}
	
		@Override
		public String toString() {
			return s;
		}
		public void setId(int id){
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
		this.filter = filter;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public boolean isIntfilter() {
		return intfilter;
	}

	public void setIntfilter(boolean intfilter) {
		this.intfilter = intfilter;
	}

	
	
	
}
