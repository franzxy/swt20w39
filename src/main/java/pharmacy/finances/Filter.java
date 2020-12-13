package pharmacy.finances;

import org.springframework.data.annotation.Id;




public  enum Filter {
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
