package pharmacy.finances;

public class Fixkosten {
    private double miete;
    private double strom;
    private double wasser;
    private double heizkosten;

    public double getMiete() {
        return miete;
    }

    public void setMiete(double miete) {
        this.miete = miete;
    }

    public double getStrom() {
        return strom;
    }

    public void setStrom(double strom) {
        this.strom = strom;
    }

    public double getWasser() {
        return wasser;
    }

    public void setWasser(double wasser) {
        this.wasser = wasser;
    }

    public double getHeizkosten() {
        return heizkosten;
    }

    public void setHeizkosten(double heizkosten) {
        this.heizkosten = heizkosten;
    }

    public Fixkosten() {
        this.miete = 0.0;
        this.strom = 0.0;
        this.wasser = 0.0;
        this.heizkosten = 0.0;
    }

    
}
