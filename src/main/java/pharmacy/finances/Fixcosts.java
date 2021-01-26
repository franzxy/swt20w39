package pharmacy.finances;

import org.springframework.util.Assert;

public class Fixcosts {

    private double rent;
    private double electricity;
    private double water;
    private double heating;

    public Fixcosts() {

        this.rent = 0.0;

        this.electricity = 0.0;

        this.water = 0.0;

        this.heating = 0.0;

    }
    
    public double getRent() {

        return rent;

    }

    public void setRent(double rent) {

        Assert.notNull(rent, "Rent must not be null!");

        this.rent = rent;

    }

    public double getElectricity() {

        return electricity;

    }

    public void setElectricity(double electricity) {

        Assert.notNull(electricity, "Electricity must not be null!");

        this.electricity = electricity;
    }

    public double getWater() {

        return water;

    }

    public void setWater(double water) {

        Assert.notNull(water, "Water must not be null!");

        this.water = water;

    }

    public double getHeating() {

        return heating;

    }

    public void setHeating(double heating) {
        
        Assert.notNull(heating, "Heating must not be null!");

        this.heating = heating;

    }
    
}
