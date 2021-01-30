package pharmacy.finances;

import org.springframework.util.Assert;
/**
 * Diese Klasse fasst alle Fixkosten zusammen um Dinge zu vereinfachen.
 * @author Lukas Luger
 */
public class Fixcosts {

    private double rent;
    private double electricity;
    private double water;
    private double heating;
    /**
     * Initialisiert die Fixkosten.
     */
    public Fixcosts() {

        this.rent = 0.0;

        this.electricity = 0.0;

        this.water = 0.0;

        this.heating = 0.0;

    }
    /**
     * Gibt die Miete zurück.
     * @return {@link Double} rent
     */
    public double getRent() {

        return rent;

    }
    /**
     * Setzt die Miete.
     * @param rent
     */
    public void setRent(double rent) {

        Assert.notNull(rent, "Rent must not be null!");

        this.rent = rent;

    }
    /**
     * Gibt die Stromkosten zurück.
     * @return {@link Double} electricity
     */
    public double getElectricity() {

        return electricity;

    }
    /**
     * Setzt die Stromkosten.
     * @param electricity
     */
    public void setElectricity(double electricity) {

        Assert.notNull(electricity, "Electricity must not be null!");

        this.electricity = electricity;
    }
    /**
     * Gibt die Wasserkosten zurück.
     * @return {@link Double} water
     */
    public double getWater() {

        return water;

    }
    /**
     * Setzt die Wasserkosten.
     * @param electricity
     */
    public void setWater(double water) {

        Assert.notNull(water, "Water must not be null!");

        this.water = water;

    }
    /**
     * Gibt die Heizkosten zurück.
     * @return {@link Double} heating
     */
    public double getHeating() {

        return heating;

    }
    /**
     * Setzt die Heizkosten.
     * @param heating
     */
    public void setHeating(double heating) {
        
        Assert.notNull(heating, "Heating must not be null!");

        this.heating = heating;

    }
    
}
