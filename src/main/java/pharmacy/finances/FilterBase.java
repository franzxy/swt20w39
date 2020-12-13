package pharmacy.finances;

public class FilterBase {
	private Filter filter;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public FilterBase() {
		this.filter=Filter.ALLE;
	}
	
	
}
