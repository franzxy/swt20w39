package pharmacy.finances;

import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.time.BusinessTime;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pharmacy.user.UserManagement;

@Component
public class FinanceDataInitializer  implements DataInitializer{
	
	private final OrderManagement<Order> orderManagement;
	private final Accountancy accountancy;
	private final BusinessTime time;
	private final UserManagement userManagement;
	private final AccountancyAdapter accountancyAdapter;

	public FinanceDataInitializer( UserManagement userManagement,OrderManagement<Order> orderManagement,  
		Accountancy accountancy, BusinessTime time, AccountancyAdapter accountancyAdapter) {
		
		Assert.notNull(userManagement, "UserManagement must not be null!");
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(accountancy, "Accountancy must not be null!");
		Assert.notNull(time, "BusinessTime must not be null!");
		Assert.notNull(accountancyAdapter, "AccountancyAdapter must not be null!");

		this.userManagement = userManagement;
		this.orderManagement = orderManagement;
		this.accountancy = accountancy;
		this.time = time;
		this.accountancyAdapter = accountancyAdapter;
	
	}

	@Override
	public void initialize() {

	}
}
