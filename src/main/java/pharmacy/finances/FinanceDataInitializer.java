package pharmacy.finances;


import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Component;

import pharmacy.user.UserManagement;










@Component

public class FinanceDataInitializer  implements DataInitializer{
	
	private final OrderManagement<Order> orderManagement;
	private final Accountancy acc;
	private final BusinessTime time;
	private final UserManagement um;
	public FinanceDataInitializer( UserManagement um,OrderManagement<Order> orderManagement,  Accountancy acc, BusinessTime time) {
		this.acc = acc;
		this.orderManagement = orderManagement;
		this.time=time;
		this.um=um;

		

		
	}

	@Override
	public void initialize() {
		
		
	}

}
