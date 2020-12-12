package pharmacy.finances;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Filter {
	private String Filterkriterium;

	public String getFilterkriterium() {
		return Filterkriterium;
	}

	public void setFilterkriterium(String filterkriterium) {
		Filterkriterium = filterkriterium;
	}

	public Filter(String filterkriterium) {
		Filterkriterium = filterkriterium;
	}
	public Filter() {}
	

}
