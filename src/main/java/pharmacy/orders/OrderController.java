package pharmacy.orders;


import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import pharmacy.inventory.Medicine;

import java.util.Optional;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
public class OrderController {

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

		return "redirect:/";
	}

	@GetMapping("/cart")
	String basket() {return "cart";}

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
	@PreAuthorize("hasRole('BOSS')")
	String orders(Model model) {

		model.addAttribute("ordersCompleted", orderManagement.findBy(OrderStatus.COMPLETED));
		return "orders";
	}
}