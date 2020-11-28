package kickstart.inventory;

import javax.persistence.Entity;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

// (｡◕‿◕｡)
// Da der Shop DVD sowie BluRay verkaufen soll ist es sinnvoll eine gemeinsame Basisklasse zu erstellen.
// Diese erbt von Product um die Catalog-Klasse aus Salespoint nutzen zu können.
// Ein Primärschlüssel ist nicht notwendig, da dieser schon in Product definiert ist, alle anderen
// JPA-Anforderungen müssen aber erfüllt werden.
@Entity
public class Medicine extends Product {

	public static enum MedicineType {
		PRESCRIPTIONONLY, WITHOUTPRESCRIPTION;
	}
	
	public static enum IngredientType {
		LABOR, SHOP;
	}

	// (｡◕‿◕｡)
	// primitive Typen oder Strings müssen nicht extra für JPA annotiert werden
	private String image, usage;
	private MedicineType type;
	private IngredientType ingredients;

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {}

	public Medicine(String name, String image, Money price, String usage, IngredientType ingredients, MedicineType type) {

		super(name, price);

		this.image = image;
		this.usage = usage;
		this.ingredients = ingredients;
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public String getUsage() {
		return usage;
	}
	
	public MedicineType getType() {
		return type;
	}
	
	public IngredientType getIngredient() {
		return ingredients;
	}
}