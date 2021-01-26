package pharmacy.inventory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.javamoney.moneta.Money;
import org.springframework.util.Assert;

import pharmacy.catalog.Medicine;

public class MedicineForm {
    @NotBlank(message="Darf nicht leer sein")
    private String description, name, tags, image;
    private String id;
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
        this.quantity=1;
        this.id="";

    }

    public Medicine toMedicine(){   

        String image2="default"; //Paste default pic here

        File pic = new File(".\\src\\main\\resources\\static\\img\\med\\"+this.image+".png");

        if(pic.exists()){

            image2=this.image;

        }

        return new Medicine(this.name, this.description, Money.of( this.price, "EUR"), 
            Money.of( this.purchasingprice, "EUR"),  Arrays.asList(tags.replace(" ", "").split(",")), this.amount,  
            this.presonly, image2, this.quantity);

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        Assert.notNull(description, "Description must not be null!");

        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        Assert.notNull(name, "Name must not be null!");

        this.name = name;

    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {

        Assert.notNull(tags, "Tags must not be null!");

        this.tags = tags;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        Assert.notNull(image, "Image must not be null!");

        this.image = image;

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {

        Assert.notNull(amount, "Amount must not be null!");

        this.amount = amount;

    }

    public boolean isPresonly() {
        return presonly;
    }

    public void setPresonly(boolean presonly) {

        Assert.notNull(presonly, "Presonly must not be null!");

        this.presonly = presonly;

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {

        Assert.notNull(price, "Price must not be null!");

        this.price = price;

    }

    public double getPurchasingprice() {
        return purchasingprice;
    }

    public void setPurchasingprice(double purchasingprice) {

        Assert.notNull(purchasingprice, "Purchasingprice must not be null!");

        this.purchasingprice = purchasingprice;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {

        Assert.notNull(quantity, "Quantity must not be null!");

        this.quantity = quantity;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        Assert.notNull(id, "Id must not be null!");

        this.id = id;
        
    }
}
   
    
    