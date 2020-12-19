package pharmacy.catalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

@Entity
public class Medicine extends Product {

	public static enum PrescriptionType {
		PRESONLY, WITHOUTPRES;
	}
	public static enum IngredientType {
		LABOR, SHOP, BOTH, MIXTURE;
	}
	public static enum MedicineType {
		CAPSULE, TABLET, OINTMENT, LIQUID, POWDER;
	}
	//private variable name, price entfernt --> name, price aus superklasse wird verwendet
	private String id, image, usage; // Identifikationsnummer, Name des Medikaments, Bild zum Medikament, Benutzt für ...
	private int restock, size; // wie viele vorrätig sein sollen, Packungsgröße
	private ArrayList<LocalDate> bbd; // Mindesthaltbarkeitsdatum und Listenlänge sagt wie viele Medikamente da sind
	private ArrayList<Medicine> ingredients; // Falls es eine Mixtur unseres Labors ist muss etwas in der Liste stehen
	private PrescriptionType presType; // Brauch man ein Rezept oder nicht
	private IngredientType ingType; // wofür wird es verwendet
	private MedicineType medType; // definiert welche Form das Medikament hat und was die 'size' bedeutet (Anzahl, ml, g)

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {}

	public Medicine(String id, String name, String image, String usage, int restock, int size, Money price,
			ArrayList<LocalDate> bbd, ArrayList<Medicine> ingredients, 
			PrescriptionType presType, IngredientType ingType, MedicineType medType) {
		
		super(name, price);

		this.id = id;
		this.image = image;
		this.usage = usage;
		this.restock = restock;
		this.size = size;
		this.bbd = bbd;
		this.ingredients = ingredients;
		this.presType = presType;
		this.ingType = ingType;
		this.medType = medType;
	}
	// getter Funktionen für alle Variablen
	// Suche/Falk: getter für name und price entfernt, damit getter aus superklasse aufgerufen werden (returnt sonst null)

	public String getImage() {
		return image;
	}
	public String getUsage() {
		return usage;
	}
	public int getRestock() {
		return restock;
	}
	public int getSize() {
		return size;
	}
	public ArrayList<LocalDate> getBBD() {
		return bbd;
	}
	public ArrayList<Medicine> getIngredients() {
		return ingredients;
	}
	public PrescriptionType getPresType() {
		return presType;
	}
	public IngredientType getIngType() {
		return ingType;
	}
	public MedicineType getMedType() {
		return medType;
	}
	// getter Funktion um die Anzahl der vorrätigen Medikamente zu bekommen
	public int getStock() {
		return bbd.size();
	}
	// ändern wie viele Medikamente vorhanden sein sollen
	public void setRestock(int restock) {
		this.restock = restock;
	}
	// neue MHD's zur Liste hinzufügen und damit Menge an Medikamenten im Lager erhöhen
	public void addNewStock() {
		
		LocalDate date = LocalDate.now().plusMonths(6);
		for (int i = bbd.size(); i <= restock; i++) {
			bbd.add(date);
		}
	}
	// entferne überfällige MHD Medikamente
	public void deleteStockOverBBD() {
		
		LocalDate date = LocalDate.now();
		for (int i = 0; i < bbd.size(); i++) {
			bbd.remove(date);
		}
	}
	// entfernt verkaufte Elemente
	public void soldMedicine(int numberSold) {
		
		for (int i = 0; i < numberSold; i++) {
			bbd.remove(0);
		}
	}
}