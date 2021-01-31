package pharmacy.user;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.salespointframework.payment.PaymentMethod;

public class PaymentCard extends PaymentMethod implements Serializable {

	private @Id @GeneratedValue long id;

	private String name;
	private String number;
	private String secure;

	public PaymentCard(String name, String number, String secure) {
		super("PaymentCard");
		this.name = name;
		this.number = number;
		this.secure = secure;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String newNumber) {
		number = newNumber;
	}

	public String getSecure() {
		return secure;
	}

	public void setSecure(String newSecure) {
		secure = newSecure;
	}
}