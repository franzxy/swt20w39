package pharmacy.catalog;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import javax.persistence.Entity;
import java.io.File;
import java.util.List;

@Entity
public class Medicine extends Product {
	private String description, image;
	private Money purchaseprice;
	private boolean presonly;
	private double amount;
	private int quantity;

	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {
	}
	
	public Medicine(String name, String description, Money price, Money purchasingprice, List<String> categories, double amount, boolean presonly, String image, int quantity){
		super(name, price);
		this.presonly=presonly;
		this.description=description;
		this.purchaseprice=purchasingprice;
		this.image=image;
		categories.forEach(cat->{
			super.addCategory(cat);
		});
		super.createQuantity(amount);
		this.amount=amount;
		this.quantity=quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Money getPurchaseprice() {
		return purchaseprice;
	}

	public void setPurchaseprice(Money purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public boolean isPresonly() {
		return presonly;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean hasImage(String med) {
		File f = new File("src/main/resources/static/img/med/" + getImage() + ".png");
		return (f.exists());
	}
}