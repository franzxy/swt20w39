package pharmacy.user;

import javax.persistence.Embeddable;

import org.salespointframework.payment.PaymentMethod;

@Embeddable
public class PayDirekt extends PaymentMethod {

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