package pharmacy.inventory;

import java.util.Arrays;

import org.javamoney.moneta.Money;

import pharmacy.catalog.Medicine;

public class MedicineForm {
    
    private String description, name, tags, image, id; 
    private double amount; 
    private int quantity;
	private boolean presonly; 
    private double price;
    private double purchasingprice;

    public MedicineForm() {
        this.description="";
        this.name="";
        this.tags="";
        this.image="";
        this.amount=0.0;
        this.presonly=false;
        this.price=0.0;
        this.purchasingprice=0.0;
        this.quantity=0;
        this.id="";
    }

    public Medicine toMedicine(){
        return new Medicine(this.name, this.description, Money.of( this.price, "EUR"), Money.of( this.purchasingprice, "EUR"),  Arrays.asList(tags.split(",")), this.amount,  this.presonly, this.image, this.quantity);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public boolean isPresonly() {
        return presonly;
    }

    public void setPresonly(boolean presonly) {
        this.presonly = presonly;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPurchasingprice() {
        return purchasingprice;
    }

    public void setPurchasingprice(double purchasingprice) {
        this.purchasingprice = purchasingprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
   
    
    