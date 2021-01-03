package pharmacy.order;

import org.springframework.data.annotation.Id;

public class OrderFilter{
	public enum Filter {
		ALLE("Alles"),
		OFFEN("Offen"),
		BEZAHLT("Bezahlt"),
		COMPLETED("Abgeschlossen"),
		EIGENE("Eigene");
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

	public OrderFilter() {
		this.filter=Filter.ALLE;
	}
	
	
}
