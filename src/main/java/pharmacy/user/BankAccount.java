package pharmacy.user;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.salespointframework.payment.PaymentMethod;

public class BankAccount extends PaymentMethod implements Serializable {

	private @Id @GeneratedValue long id;

	private String name;
	private String iban;
	private String bic;

	public BankAccount(String name, String iban, String bic) {
		super("BankAccount");
		this.name = name;
		this.iban = iban;
		this.bic = bic;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String newIban) {
		iban = newIban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String newBic) {
		bic = newBic;
	}
}