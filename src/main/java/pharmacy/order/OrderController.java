package pharmacy.order;

import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.*;
import org.springframework.web.bind.annotation.*;
import pharmacy.catalog.Medicine;

import java.util.Optional;

import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("cart")
class OrderController {

	private static final Quantity NONE = Quantity.of(0);

	private final OrderManagement<Order> orderManagement;
	private final UniqueInventory<UniqueInventoryItem> inventory;

	OrderController(OrderManagement<Order> orderManagement, UniqueInventory<UniqueInventoryItem> inventory) {

		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		this.inventory = inventory;
		this.orderManagement = orderManagement;
	}

	@ModelAttribute("cart")
	Cart initializeCart() {
		return new Cart();
	}

	@PostMapping("/cart")
	String addDisc(@RequestParam("pid") Medicine medicine, @RequestParam("number") int number, @ModelAttribute Cart cart) {


		var maxQuantity = inventory.findByProductIdentifier(medicine.getId()) //
				.map(InventoryItem::getQuantity) //
				.orElse(NONE);

		Quantity quantity = cart.get()
				.filter(item -> item.getProduct().getId().equals(medicine.getId()))
				.findAny()
				.map(item -> item.getQuantity())
				.orElse(Quantity.NONE);
		Quantity amount = quantity
				.add(Quantity.of(number));

		if(maxQuantity.isGreaterThanOrEqualTo(amount)) {
			cart.addOrUpdateItem(medicine, Quantity.of(number));
		} else {
			cart.addOrUpdateItem(medicine, maxQuantity.subtract(quantity));
		}

		return "redirect:index";
	}

	@PostMapping("/cart/update")
	String increaseDiscs(@RequestParam("pid") String cartItemId, @RequestParam("number") int number, @ModelAttribute Cart cart) {
		Optional<CartItem> mayBeCartItem = cart.getItem(cartItemId);
		if (mayBeCartItem.isPresent()) {
			CartItem item = mayBeCartItem.get();
			var maxQuantity = inventory.findByProductIdentifier(item.getProduct().getId())
					.map(InventoryItem::getQuantity)
					.orElse(NONE);
			Quantity amount = item.getQuantity().add(Quantity.of(number));
			if (maxQuantity.isGreaterThanOrEqualTo(amount)  && amount.isGreaterThan(Quantity.NONE) ) {
				cart.addOrUpdateItem(item.getProduct(), Quantity.of(number));
			}
			if (amount.isEqualTo(Quantity.NONE)) {
				cart.removeItem(cartItemId);
			}
		}

		return "cart";
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
}
