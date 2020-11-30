package pharmacy.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

// (｡◕‿◕｡)
// Da der Shop DVD sowie BluRay verkaufen soll ist es sinnvoll eine gemeinsame Basisklasse zu erstellen.
// Diese erbt von Product um die Catalog-Klasse aus Salespoint nutzen zu können.
// Ein Primärschlüssel ist nicht notwendig, da dieser schon in Product definiert ist, alle anderen
// JPA-Anforderungen müssen aber erfüllt werden.
@Entity
public class Medicine extends Product {

	public static enum PrescriptionType {
		BLURAY, DVD;
	}

	// (｡◕‿◕｡)
	// primitive Typen oder Strings müssen nicht extra für JPA annotiert werden
	private String genre, image;
	private PrescriptionType type;

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {}

	public Medicine(String name, String image, Money price, String genre, PrescriptionType type) {

		super(name, price);

		this.image = image;
		this.genre = genre;
		this.type = type;
	}

	public String getGenre() {
		return genre;
	}

	public String getImage() {
		return image;
	}

	public PrescriptionType getType() {
		return type;
	}
}