package pharmacy.order;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

public class OrderFilter{
	public enum Filter {
		ALLE("Alles"),
		OFFEN("Offen"),
		BEZAHLT("Bezahlt"),
		COMPLETED("Abgeschlossen");
		
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

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {

		Assert.notNull(filter, "Filter must not be null!");

		this.filter = filter;
	}

	public OrderFilter() {
		this.filter=Filter.ALLE;
	}
	
	
}
