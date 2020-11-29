package pharmacy.inventory;

import javax.persistence.Entity;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

@Entity
public class Medicine extends Product {

	public static enum PrescriptionType {
		PRESCRIPTIONONLY, WITHOUTPRESCRIPTION, BOTH;
	}
	
	public static enum IngredientType {
		LABOR, SHOP, BOTH;
	}

	// (｡◕‿◕｡)
	// primitive Typen oder Strings müssen nicht extra für JPA annotiert werden
	private String image, usage;
	private PrescriptionType type;
	private IngredientType ingredients;

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {}

	public Medicine(String name, String image, Money price, String usage, IngredientType ingredients, PrescriptionType type) {

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
	
	public PrescriptionType getType() {
		return type;
	}
	
	public IngredientType getIngredient() {
		return ingredients;
	}
}