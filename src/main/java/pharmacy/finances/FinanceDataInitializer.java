package pharmacy.finances;


import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Component;







@Component

public class FinanceDataInitializer  implements DataInitializer{
	
	private final OrderManagement<Order> orderManagement;
	private final Accountancy acc;
	private final BusinessTime time;
	
	public FinanceDataInitializer( OrderManagement<Order> orderManagement,  Accountancy acc, BusinessTime time) {
		this.acc = acc;
		this.orderManagement = orderManagement;
		
		this.time=time;
		
		
		
	}

	@Override
	public void initialize() {
		
		this.acc.add(new AccountancyEntry(Money.of(23,"EUR")," Test"));
	}

}
