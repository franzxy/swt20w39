package pharmacy.user;

import java.io.Serializable;

import org.salespointframework.payment.PaymentMethod;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class PayDirekt extends PaymentMethod implements Serializable {

	private @Id @GeneratedValue long id;

	private String name;

	public PayDirekt(String name) {
		super("PayDirekt");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}
}