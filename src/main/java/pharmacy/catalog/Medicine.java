package pharmacy.catalog;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

@Entity
public class Medicine extends Product {
	private String description, image;
	private Money purchaseprice;
	private boolean presonly;
	@SuppressWarnings({ "unused", "deprecation" })
	private Medicine() {
		
	}

	public Medicine(String name, String description, Money price, Money purchasingprice, List<String> categories, double amount, boolean presonly, String image){
		super(name, price);
		//System.out.println(metric);
		this.presonly=presonly;
		this.description=description;
		this.purchaseprice=purchasingprice;
		this.image=image;
		categories.forEach(cat->{
			super.addCategory(cat);
		});
		super.createQuantity(amount);
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

}