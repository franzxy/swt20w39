package pharmacy.catalog;

public class SearchForm {
	private String searchTerm, medType, ingType;
	private boolean nopres;

	public String getSearchTerm() {
		return this.searchTerm;
	}

	public String getMedType() {
		return this.medType;
	}

	public String getIngType() {
		return this.ingType;
	}

	public boolean getNoPres() {
		return this.nopres;
	}

	public void setSearchTerm(String s) {
		this.searchTerm = s;
	}

	public void setIngType(String s) {
		this.ingType = s;
	}

	public void setMedType(String s) {
		this.medType = s;
	}

	public void setNopres(boolean b) {
		this.nopres = b;
	}
}
