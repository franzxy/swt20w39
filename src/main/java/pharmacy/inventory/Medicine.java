package pharmacy.inventory;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

@Entity
public class Medicine extends Product {

	public static enum PrescriptionType {
		PRESCRIPTIONONLY, WITHOUTPRESCRIPTION;
	}
	
	public static enum IngredientType {
		LABOR, SHOP, BOTH, MIXTURE;
	}
	
	public static enum MedicineType {
		PILL, OINTMENT, CAPSULE, POWDER, LIQUID;
	}

	// (｡◕‿◕｡)
	// primitive Typen oder Strings müssen nicht extra für JPA annotiert werden
	private String id;
	private ArrayList<Date> mhd; 
	private ArrayList<String> ingredients; 
	private String image, usage;
	private int size, restockNumber;
	private PrescriptionType presType;
	private IngredientType ingredientType;
	private MedicineType medicineType;

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {}

	public Medicine(String id, String name, Money price, String usage, String image, int size, int restockNumber, 
			ArrayList<Date> mhd, ArrayList<String> ingredients,
			IngredientType ingredientType, PrescriptionType presType, MedicineType medicineType) {

		super(name, price);
		
		this.id = id;
		this.image = image;
		this.usage = usage;
		this.size = size;
		this.restockNumber = restockNumber;
		this.ingredientType = ingredientType;
		this.presType = presType;
		this.mhd = mhd;
		this.ingredients = ingredients;
	}

	public String getID() {
		return id;
	}
	
	public String getImage() {
		return image;
	}

	public String getUsage() {
		return usage;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getRestockNumber() {
		return restockNumber;
	}
	
	public ArrayList<Date> getMHD() {
		return mhd;
	}
	
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	
	public PrescriptionType getPrescriptionType() {
		return presType;
	}
	
	public IngredientType getIngredientType() {
		return ingredientType;
	}
	
	public MedicineType getMedicineType() {
		return medicineType;
	}
	
}