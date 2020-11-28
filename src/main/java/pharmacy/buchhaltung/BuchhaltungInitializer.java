package buchhaltung;

import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class BuchhaltungInitializer implements DataInitializer{
	
	@Autowired
	private final Accountancy accountancy;
	
	public BuchhaltungInitializer(Accountancy accountancy) {
		
		Assert.notNull(accountancy, "Accountancy must not be null!");
		
		this.accountancy = accountancy;
	}

	@Override
	public void initialize() {
		
	}

}
