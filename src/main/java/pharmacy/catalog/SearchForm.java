package pharmacy.catalog;

public class SearchForm {
	private String searchTerm;
	private String tag;
	private boolean noPres;

	public String getSearchTerm() {
		return this.searchTerm;
	}

	public String getTag() {
		return tag;
	}

	public boolean getNoPres() {
		return this.noPres;
	}

	public void setSearchTerm(String s) {
		this.searchTerm = s;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setNoPres(boolean b) {
		this.noPres = b;
	}

	public boolean isSelected(String t) {
		return t.equals(tag);
	}
}