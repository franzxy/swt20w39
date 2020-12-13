package pharmacy.finances;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
