package pharmacy.finances;

import org.springframework.data.annotation.Id;

public class FilterForm{
	public enum Filter {
		ALLE("Alles"),
		OBEST("Online Bestellungen"),
		VERK("Verkäufe"),
		PRAXA("Praxis A"),
		PRAXB("Praxis B"),
		PRAXC("Praxis C"),
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

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public FilterForm() {
		this.filter=Filter.ALLE;
	}
	
	
}
