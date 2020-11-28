package kickstart.inventory;

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
	// (｡◕‿◕｡)
	// Jede Disc besitzt mehrere Kommentare, eine "1 zu n"-Beziehung -> @OneToMany für JPA
	// cascade gibt an, was mit den Kindelementen (Comment) passieren soll wenn das Parentelement
	// (Disc) mit der Datenbank "interagiert"
	@OneToMany(cascade = CascadeType.ALL) //
	private List<Comment> comments = new ArrayList<>();

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {}

	public Medicine(String name, String image, Money price, String usage, IngredientType ingredients, MedicineType type) {

		super(name, price);

		this.image = image;
		this.usage = usage;
		this.ingredients = ingredients;
		this.type = type;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	// (｡◕‿◕｡)
	// Es ist immer sinnvoll sich zu überlegen wie speziell der Rückgabetyp sein sollte
	// Da sowies nur über die Kommentare iteriert wird, ist ein Iterable<T> das sinnvollste.
	// Weil wir keine Liste zurück geben, verhindern wir auch, dass jemand die comments-Liste
	// einfach durch clear() leert. Deswegen geben auch so viele Salespoint Klassen nur
	// Iterable<T> zurück ;)
	public Iterable<Comment> getComments() {
		return comments;
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