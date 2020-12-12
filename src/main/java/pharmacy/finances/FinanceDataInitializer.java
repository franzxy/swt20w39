package pharmacy.finances;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.ProductPaymentEntry;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;

import org.salespointframework.order.Order;
import org.springframework.stereotype.Component;



@Component

public class FinanceDataInitializer  implements DataInitializer{
	private final Filter filter;
	private final OrderManagement<Order> orderManagement;
	private final UserAccountManagement um;
	private final Accountancy acc;
	private final BusinessTime time;
	public FinanceDataInitializer(Filter filter, OrderManagement<Order> orderManagement, UserAccountManagement um, Accountancy acc, BusinessTime time) {
		
		this.acc = acc;
		this.filter = filter;
		this.orderManagement = orderManagement;
		this.um = um;
		this.time=time;
		
	}

	@Override
	public void initialize() {
		this.filter.setFilterkriterium("alle");
		// TODO Auto-generated method stub
		UserAccount u1 = this.um.create("Hans", Password.UnencryptedPassword.of("123"), Role.of("normalo"));
		
		this.um.save(u1);
		this.um.enable(u1.getId());
		
		Order order= new Order(u1,Cash.CASH);
		
		order.addChargeLine(Money.of(10, "EUR"), "MeDiKaMeNt");
		
		this.orderManagement.payOrder(order);
		this.orderManagement.save(order);
		//this.acc.add(ProductPaymentEntry.of(order, "This is something new"));
	}

}
