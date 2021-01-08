package pharmacy.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/cart")
	String basket() {
		return "cart";
	}

	@PostMapping("/cart")
	String addItem(@RequestParam("pid") Medicine item, @RequestParam("number") int number, @ModelAttribute Cart cart) {

		int amount = number <= 0 ? 1 : number;

		cart.addOrUpdateItem(item, Quantity.of(amount));

		return "redirect:/";
	}

	@PostMapping("/clearcart")
	String emptycart(@ModelAttribute Cart cart){
		cart.clear();
		return "redirect:/cart";
	}

	@PostMapping("/checkout")
	String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {

		return userAccount.map(account -> {

			var order = new Order(account, Cash.CASH);
			

			cart.addItemsTo(order);

			orderManagement.payOrder(order);

			cart.clear();

			return "redirect:/";
		}).orElse("redirect:/cart");
	}

	@GetMapping("/orders")
	String orders(Model model, @LoggedIn Optional<UserAccount> userAccount) {
		List<Order> ret =List.of() ;
		if(!userAccount.isEmpty()){
			if(userAccount.get().hasRole(Role.of("BOSS"))){
				ArrayList<Order> all = new ArrayList<Order>();
				all.addAll(this.orderManagement.findBy(OrderStatus.COMPLETED).toList());
				all.addAll(this.orderManagement.findBy(OrderStatus.PAID).toList());
				all.addAll(this.orderManagement.findBy(OrderStatus.OPEN).toList()); 
				ret=all;
				
			}
			if(userAccount.get().hasRole(Role.of("CUSTOMER"))){
				ret=this.orderManagement.findBy(userAccount.get()).toList();
			}
			if(userAccount.get().hasRole(Role.of("EMPLOYEE"))){
				ret=this.orderManagement.findBy(OrderStatus.PAID).toList();
			}
		}
		model.addAttribute("rech",ret);
		model.addAttribute("filter", new OrderFilter());
		return "orders";
	}

	@PostMapping("/orders")
	String postorders(@ModelAttribute OrderFilter filter,Model model, @LoggedIn Optional<UserAccount> userAccount) {
		List<Order> ret =List.of() ;
		if(!userAccount.isEmpty()){
			if(userAccount.get().hasRole(Role.of("BOSS"))){
				ArrayList<Order> all = new ArrayList<Order>();
				all.addAll(this.orderManagement.findBy(OrderStatus.COMPLETED).toList());
				all.addAll(this.orderManagement.findBy(OrderStatus.PAID).toList());
				all.addAll(this.orderManagement.findBy(OrderStatus.OPEN).toList()); 
				switch(filter.getFilter()){
					case OFFEN: ret= this.orderManagement.findBy(OrderStatus.OPEN).toList();break;
					case BEZAHLT: ret=this.orderManagement.findBy(OrderStatus.PAID).toList();break;
					case COMPLETED: ret= this.orderManagement.findBy(OrderStatus.COMPLETED).toList();break;
					case EIGENE: ret= this.orderManagement.findBy(userAccount.get()).toList();break;
					default: ret=all;break;
				}
			}
			if(userAccount.get().hasRole(Role.of("CUSTOMER"))){
				ret=this.orderManagement.findBy(userAccount.get()).toList();
			}
			if(userAccount.get().hasRole(Role.of("EMPLOYEE"))){
				ret=this.orderManagement.findBy(OrderStatus.PAID).toList();
			}
		}
		model.addAttribute("rech", ret);
		model.addAttribute("filter", filter);
		return "orders";
	}

	@GetMapping("/orders/{id}")
	public String detail(@PathVariable OrderIdentifier id, Model model, @LoggedIn Optional<UserAccount> userAccount) {
		
		if(userAccount.isEmpty()){
			model.addAttribute("rech", this.orderManagement.findBy(userAccount.get()).toList());
			model.addAttribute("filter", new OrderFilter());
			return "orders";
		}
		
		Order order=this.orderManagement.get(id).get();
		model.addAttribute("det", order);
			
		

		return "orderdetails";
	}
	
	@GetMapping("/orders/{id}/complete")
	public String complete(@PathVariable OrderIdentifier id, Model model){
		if(this.orderManagement.get(id).isPresent()){
			Order o=this.orderManagement.get(id).get();
			this.orderManagement.completeOrder(o);
		}
		return "redirect:/orders";
	}

	@GetMapping("/orders/{id}/cancel")
	public String cancel(@PathVariable OrderIdentifier id, Model model){
		if(this.orderManagement.get(id).isPresent()){
			try{
				Order o=this.orderManagement.get(id).get();
				this.orderManagement.cancelOrder(o, "Canceled by Boss");
			}catch(ClassCastException e){
				Order o=this.orderManagement.get(id).get();
				this.orderManagement.delete(o);
			}
		}
		return "redirect:/orders";
	}

	@GetMapping("/myorders")
	String myorders(Model model, @LoggedIn Optional<UserAccount> userAccount) {
		List<Order> ret =List.of() ;
		if(!userAccount.isEmpty()){
			ret=this.orderManagement.findBy(userAccount.get()).toList();
		}
		model.addAttribute("rech", ret);
		return "myorders";
	}
}
