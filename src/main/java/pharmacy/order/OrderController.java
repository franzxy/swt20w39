package pharmacy.order;

import java.util.List;
import java.util.Optional;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pharmacy.catalog.Medicine;

@Controller
@SessionAttributes("cart")
public class OrderController {
	//private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	private final OrderManagement<Order> orderManagement;

	OrderController(OrderManagement<Order> orderManagement) {

		Assert.notNull(orderManagement, "OrderManagement must not be null.");
		this.orderManagement = orderManagement;
	}

	@ModelAttribute("cart")
	Cart initializeCart() {
		return new Cart();
	}

	@PostMapping("/cart")
	String addItem(@RequestParam("pid") Medicine item, @RequestParam("number") int number, @ModelAttribute Cart cart) {

		int amount = number <= 0 ? 1 : number;

		cart.addOrUpdateItem(item, Quantity.of(amount));
		System.out.println(cart.getPrice().getNumber().doubleValue());
		return "redirect:/";
	}

	@GetMapping("/cart")
	String basket() {
		return "cart";
	}

	@PostMapping("/checkout")
	String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {

		return userAccount.map(account -> {

			var order = new Order(account, Cash.CASH);
			

			cart.addItemsTo(order);

			orderManagement.payOrder(order);
			orderManagement.completeOrder(order);

			cart.clear();

			return "redirect:/";
		}).orElse("redirect:/cart");
	}

	@GetMapping("/orders")
	String orders(Model model, @LoggedIn Optional<UserAccount> userAccount) {
		model.addAttribute("rech", this.orderManagement.findBy(userAccount.get()).toList());
		model.addAttribute("filter", new OrderFilter());
		return "orders";
	}
	@PostMapping("/orders")
	String postorders(@ModelAttribute OrderFilter filter,Model model, @LoggedIn Optional<UserAccount> userAccount) {
		List<Order> ret =List.of() ;
		if(!userAccount.isEmpty()){
			if(userAccount.get().hasRole(Role.of("BOSS"))){
				List<Order> all = this.orderManagement.findBy(OrderStatus.COMPLETED).toList();
				//all.addAll(this.orderManagement.findBy(OrderStatus.PAID).toList());
				//all.addAll(this.orderManagement.findBy(OrderStatus.OPEN).toList()); 
				switch(filter.getFilter()){
					case OFFEN: ret= this.orderManagement.findBy(OrderStatus.OPEN).toList();break;
					case BEZAHLT: ret=this.orderManagement.findBy(OrderStatus.PAID).toList();break;
					case COMPLETED: ret= this.orderManagement.findBy(OrderStatus.COMPLETED).toList();break;
					default: ret=all;break;
				}
			}else{
				ret=this.orderManagement.findBy(userAccount.get()).toList();
			}
		}
		model.addAttribute("rech", ret);
		model.addAttribute("filter", filter);
		return "orders";
	}
}
