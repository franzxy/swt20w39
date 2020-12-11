package pharmacy.search;

public class SearchForm {

	private String searchTerm;
	private boolean nopres;

	public String getSearchTerm() {
		return this.searchTerm;
	}

	public boolean getNoPres() {
		return nopres;
	}

	public void setSearchTerm(String s) {
		this.searchTerm = s;
	}

	public void setNopres(boolean b) {
		this.nopres = b;
	}
}
