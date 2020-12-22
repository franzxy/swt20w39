package pharmacy.inventory;

import pharmacy.catalog.Medicine.IngredientType;
import pharmacy.catalog.Medicine.MedicineType;
import pharmacy.catalog.Medicine.PrescriptionType;

public class MedicineForm {
    private String image, usage, name; 
	private int size, amount; 
	private String bbd; 
    private String ingredient1; 
    private String ingredient2;
    private String ingredient3;
	private PrescriptionType presType; 
	private IngredientType ingType; 
    private MedicineType medType;

    private double price;
    public MedicineForm() {
        this.image = "";
        this.usage = "bsp";
        this.size = 2;
        this.name="name";
        this.amount = 1;
        this.bbd = "2000-01-01";
        this.ingredient1 = "";
        this.ingredient2 = "";
        this.ingredient3 = "";
        this.presType = PrescriptionType.PRESONLY;
        this.ingType = IngredientType.BOTH;
        this.medType = MedicineType.CAPSULE;
        this.price=0.0;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBbd() {
        return bbd;
    }

    public void setBbd(String bbd) {
        //System.out.println(bbd);
        this.bbd=bbd;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredients) {
        this.ingredient1 = ingredients;
    }
    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredients) {
        this.ingredient2 = ingredients;
    }
    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredients) {
        this.ingredient3 = ingredients;
    }
    public PrescriptionType getPresType() {
        return presType;
    }

    public void setPresType(PrescriptionType presType) {
        this.presType = presType;
    }

    public IngredientType getIngType() {
        return ingType;
    }

    public void setIngType(IngredientType ingType) {
        this.ingType = ingType;
    }

    public MedicineType getMedType() {
        return medType;
    }

    public void setMedType(MedicineType medType) {
        this.medType = medType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    

}
