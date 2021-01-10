package pharmacy.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchForm {
	private String searchTerm;
	private String tag;
	private boolean nopres;

	private static final Logger LOG = LoggerFactory.getLogger(SearchForm.class);

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

	public boolean isSelected(String t) {


		LOG.info(t);
		LOG.info(tag);
		return t.equals(tag);

	}
}
