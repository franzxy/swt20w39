package kickstart.finances;

import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.accountancy.ProductPaymentEntry;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Password.EncryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManagement;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FinanceController {
	
	private final Accountancy acc;
	private final OrderManagement<Order> orderManagement;
	private final UserAccountManagement um;
	
	public FinanceController(Accountancy acc, OrderManagement<Order> orderManagement, UserAccountManagement um) {
		Assert.notNull(um, "OrderManagement must not be null!");
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(acc, "OrderManagement must not be null!");
		this.orderManagement = orderManagement;
		this.acc=acc;
		this.um=um;
		
		//BSP:
		UserAccount u1 = this.um.create("Hans", Password.UnencryptedPassword.of("123"), Role.of("normalo"));
		
		this.um.save(u1);
		this.um.enable(u1.getId());
		
		Order order= new Order(u1,Cash.CASH);
		
		order.addChargeLine(Money.of(10, "EUR"), "MeDiKaMeNt");
		
		this.orderManagement.payOrder(order);
		this.orderManagement.save(order);
	
		//Warum??? laut doku passiert das Automatisch!!
		this.acc.add(ProductPaymentEntry.of(order, "This is something new"));
		
	}

	@GetMapping("/finances")
	public String finances(Model model) {
		List<AccountancyEntry> ret=this.acc.findAll().toList();
		model.addAttribute("entries", ret);
		return "finances";
	}
}
