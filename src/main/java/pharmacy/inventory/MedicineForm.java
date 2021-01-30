package pharmacy.inventory;

import java.io.File;
import java.util.Arrays;

import javax.validation.constraints.NotBlank;

import org.javamoney.moneta.Money;
import org.springframework.util.Assert;

import pharmacy.catalog.Medicine;
/**
 * Medizin Formular zum Editieren und hinzufügen neuer Medikamente notwendig.
 * @author Lukas Luger
 */
public class MedicineForm {
    @NotBlank(message="Darf nicht leer sein")
    private String description, name, tags, image;
    private String id;
    private double amount; 
    private int quantity;
	private boolean presonly; 
    private double price;
    private double purchasingprice;
    /**
     * Initialisiert alle Attribute.
     */
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
    /**
     * Erzeugt aus den Attributen ein Objekt des Typs {@link Medicine}.
     * @return {@link Medicine}
     */
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
    /**
     * Gibt die Beschreibung zurück
     * @return {@link String} description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Setzt die Beschreibung.
     * @param description
     */
    public void setDescription(String description) {

        Assert.notNull(description, "Description must not be null!");

        this.description = description;

    }
    /**
    * Gibt den namen zurück.
    * @return {@link String} name
    */
    public String getName() {
        return name;
    }
    /**
     * Setzt den Namen.
     * @param name
     */
    public void setName(String name) {

        Assert.notNull(name, "Name must not be null!");

        this.name = name;

    }
    /**
     * Gibt die Tags zurück.
     * @return {@link String} tags
     */
    public String getTags() {
        return tags;
    }
    /**
     * Setzt die Tags.
     * @param tags
     */
    public void setTags(String tags) {

        Assert.notNull(tags, "Tags must not be null!");

        this.tags = tags;

    }
    /**
     * Gibt den Bild Namen.
     * @return {@link String} image
     */
    public String getImage() {
        return image;
    }
    /**
     * Setzt den Bild Namen.
     * @param image
     */
    public void setImage(String image) {

        Assert.notNull(image, "Image must not be null!");

        this.image = image;

    }
    /**
     * Gibt das Gewicht zurück.
     * @return {@link Double} amount
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Setzt das Gewicht.
     * @param amount
     */
    public void setAmount(double amount) {

        Assert.notNull(amount, "Amount must not be null!");

        this.amount = amount;

    }
    /**
     * Gibt zurück ob das Medikament Verschreibungspflichtig ist.
     * @return {@link Boolean} presonly
     */
    public boolean isPresonly() {
        return presonly;
    }
    /**
     * Setzt die Verschreibungspflichtigkeit.
     * @param presonly
     */
    public void setPresonly(boolean presonly) {

        Assert.notNull(presonly, "Presonly must not be null!");

        this.presonly = presonly;

    }
    /**
     * Gibt den Preis zurück.
     * @return {@link Double} price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Setzt den Preis.
     * @param price
     */
    public void setPrice(double price) {

        Assert.notNull(price, "Price must not be null!");

        this.price = price;

    }
    /**
     * Gibt den Einkaufspreis zurück.
     * @return {@link Double} purchasingprice
     */
    public double getPurchasingprice() {
        return purchasingprice;
    }
    /**
     * Setzt den Einkaufspreis.
     * @param purchasingprice
     */
    public void setPurchasingprice(double purchasingprice) {

        Assert.notNull(purchasingprice, "Purchasingprice must not be null!");

        this.purchasingprice = purchasingprice;

    }
    /**
     * Gibt die Soll-Menge zurück.
     * @return {@link Integer} quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Setzt die Menge.
     * @param quantity
     */
    public void setQuantity(int quantity) {

        Assert.notNull(quantity, "Quantity must not be null!");

        this.quantity = quantity;

    }
    /**
     * Gibt die id zurück.
     * @return {@link String} id
     */
    public String getId() {
        return id;
    }
    /**
     * Setzt die id.
     * @param id
     */
    public void setId(String id) {

        Assert.notNull(id, "Id must not be null!");

        this.id = id;
        
    }
}
   
    
    