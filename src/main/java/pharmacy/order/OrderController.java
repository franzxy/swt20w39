package pharmacy.order;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pharmacy.catalog.Medicine;
import pharmacy.user.Address;
import pharmacy.user.UserManagement;

@EnableScheduling
@Controller
@SessionAttributes("cart")
public class OrderController {
	//private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private final UserManagement userManagement;
	@Autowired
	private final OrderManagement<Order> orderManagement;
	@Autowired
	private final UniqueInventory<UniqueInventoryItem> inventory;
	//@Autowired
	//private final Map<String, Integer> waitlist;
	private boolean completionsuccess;
	private Order failedorder;
	public Cart cart;
	private Map<ProductIdentifier, Integer> quan;
	private final BusinessTime time;

	OrderController(OrderManagement<Order> orderManagement, UniqueInventory<UniqueInventoryItem> inventory, UserManagement userManagement, BusinessTime time) {

		Assert.notNull(orderManagement, "OrderManagement must not be null.");
		this.orderManagement = orderManagement;
		Assert.notNull(inventory, "Inventory must not be null.");
		this.inventory = inventory;
		//this.waitlist=waitlist;
		this.completionsuccess=true;
		this.failedorder=null;
		this.quan=new HashMap<ProductIdentifier, Integer>();
		this.userManagement = userManagement;
		this.cart = new Cart();
		this.time = time;
	}

	@ModelAttribute("cart")
	Cart initializeCart() {
		return cart;
	}

	@Bean
	public Cart getCart() {
		return cart;
	}

	private boolean haspresonly(Cart c){
		if(c.isEmpty()) return false;
		ArrayList<Boolean> ispresonly=new ArrayList<Boolean>();
		c.get().forEach(item -> ispresonly.add(((Medicine)item.getProduct()).isPresonly()));
		for(boolean b:ispresonly) if(b) return true;
		return false;
	}
	@GetMapping("/cart")
	String basket(@ModelAttribute Cart cart, Model model) {
		HashMap<String, Integer> availability = new HashMap<String, Integer>();
		cart.forEach(cartitem->{
			Medicine Med= (Medicine) cartitem.getProduct();
			int quan=inventory.findByProduct(Med).get().getQuantity().getAmount().intValue();
			availability.put(cartitem.getId(), quan);
		});
		model.addAttribute("availability", availability);
		return "cart";
	}

	@PostMapping("/cart")
	String addItem(@RequestParam("pid") Medicine item, @RequestParam("number") int number, @ModelAttribute Cart cart) {
		
		int amount = number <= 0 ? 1 : number;
		
		cart.addOrUpdateItem(item, Quantity.of(amount));
		
	
		return "redirect:/";
	}
	@GetMapping("/cart/{id}/delete")
	String deleteItem(@PathVariable String id, @ModelAttribute Cart cart, Model model) {
		HashMap<String, Integer> availability = new HashMap<String, Integer>();
		cart.forEach(cartitem->{
			Medicine Med= (Medicine) cartitem.getProduct();
			int quan=inventory.findByProduct(Med).get().getQuantity().getAmount().intValue();
			availability.put(cartitem.getId(), quan);
		});
		cart.removeItem(id);
		model.addAttribute("availability", availability);
		return "cart";
	}

	
	@GetMapping("/updatecart")
	String basket2() {
		return "redirect:/cart";
	}

	@PostMapping("/updatecart")
	String updateItem(@RequestParam("pid") String item, @RequestParam("number") int number, @ModelAttribute Cart cart) {
		CartItem item2 = cart.getItem(item).get();
		int amount = number <= 0 ? 1 : number;
		cart.addOrUpdateItem(item2.getProduct(), Quantity.of(amount).subtract(item2.getQuantity()));
		return "redirect:/cart";
	}

	@PostMapping("/clearcart")
	String emptycart(@ModelAttribute Cart cart){
		cart.clear();
		return "redirect:/cart";
	}

	@GetMapping("/checkout")
	@PreAuthorize("isAuthenticated()")
	String checkout(Model model, AddressForm addressForm, InsuranceForm insuranceForm, Cart cart) {

		if (cart.isEmpty()) {
			return "redirect:/cart";
		}
		model.addAttribute("haspresonly", this.haspresonly(cart));
		model.addAttribute("cart", cart);
		model.addAttribute("insuranceForm", insuranceForm);
		model.addAttribute("addressForm", addressForm);
		model.addAttribute("user", userManagement.currentUser().get());
		return "checkout";
	}

	@GetMapping("/checkout/address")
	@PreAuthorize("isAuthenticated()")
	String checkoutAddress(Model model, InsuranceForm insuranceForm, AddressForm addressForm, Cart cart) {

		if (cart.isEmpty()) {
			return "redirect:/cart";
		}
		model.addAttribute("haspresonly", this.haspresonly(cart));
		model.addAttribute("cart", cart);
		model.addAttribute("insuranceForm", insuranceForm);
		model.addAttribute("addressForm", addressForm);
		model.addAttribute("user", userManagement.currentUser().get());
		return "checkout";
	}
	
	@PostMapping("/checkout/address")
	@PreAuthorize("isAuthenticated()")
	String addCheckoutAddress(Model model, Cart cart, @Valid @ModelAttribute("addressForm")AddressForm addressForm, Errors result) {
		
		if (cart.isEmpty()) {
			return "redirect:/cart";
		}
		model.addAttribute("haspresonly", this.haspresonly(cart));
		model.addAttribute("cart", cart);
		model.addAttribute("user", userManagement.currentUser().get());
		
		if (result.hasErrors()) {
			return "checkout";
		}
		
		userManagement.currentUser().get().changeAddress(new Address(addressForm.getName(), addressForm.getStreet(), addressForm.getPostCode(), addressForm.getCity()));

		return "redirect:/checkout";
	}
	
	@PostMapping("/checkout")
	String buy(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {
		var user = userManagement.currentUser().get();
		if (user.getAddress().toString().isEmpty()) {

			return "redirect:/checkout";
		}
		
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

		boolean itsBusinessTime = ((time.getTime().getHour() >= 6) && (time.getTime().getHour() < 20) && (time.getTime().getDayOfWeek() != DayOfWeek.SUNDAY ));
		model.addAttribute("TimeToDoBusiness", itsBusinessTime);

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
			o.getOrderLines().get().forEach(orderline -> {
				int quan = inventory.findByProductIdentifier(orderline.getProductIdentifier()).get().getQuantity().getAmount().intValue();
				int orderd = orderline.getQuantity().getAmount().intValue();
				this.quan.put(orderline.getProductIdentifier(), quan);
				if(!(quan>=orderd)){
					this.fail();
				}
			});
			if(this.completionsuccess){
				this.orderManagement.completeOrder(o);
				return "redirect:/orders";
			}else{
				this.completionsuccess=true;
				this.failedorder=o;
				return "redirect:/ordercompletionfail";
			}
			
		
		}
		return "redirect:/orders";
	}
	private void fail(){
		this.completionsuccess=false;
	}
	@GetMapping("/ordercompletionfail")
	public String orderfail(Model model){
		
		model.addAttribute("order", this.failedorder);
		model.addAttribute("quantity", this.quan);
		return "ordercompletionfail";
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
