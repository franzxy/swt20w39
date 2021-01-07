package pharmacy.catalog;

public class SearchForm {
	private String searchTerm;
	private String tag;
	private boolean nopres;

	public String getSearchTerm() {
		return this.searchTerm;
	}

	public String getTag() {
		return tag;
	}

	public boolean getNoPres() {
		return this.nopres;
	}

	public void setSearchTerm(String s) {
		this.searchTerm = s;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setNopres(boolean b) {
		this.nopres = b;
	}
}
