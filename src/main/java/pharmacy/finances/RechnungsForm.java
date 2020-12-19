package pharmacy.finances;

public class RechnungsForm {
    private String id;
    private String username;
    private String uemail;
    private String date;
    private double betrag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public RechnungsForm() {
        id="";
        username="";
        uemail="";
        date="";
        betrag=0.0;
    }
     

    
}
