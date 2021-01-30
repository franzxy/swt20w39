package pharmacy.order;

import java.time.DayOfWeek;
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
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
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
import pharmacy.user.PayDirekt;
import pharmacy.user.UserManagement;
/**
 * Ein Controller für:
 * <ul>
 * <li>Bestellübersicht</li>
 * <li>Warenkorb</li>
 * <li>Bezahlung</li>
 * <li>Bearbeiten von Bestellungen</li>
 * </ul>
 * @author Timon Trettin
 * @author Lukas Luger
 */
@EnableScheduling
@Controller
@SessionAttributes("cart")
public class OrderController {
	
	@Autowired
	private final UserManagement userManagement;

	@Autowired
	private final OrderManagement<Order> orderManagement;

	@Autowired
	private final UniqueInventory<UniqueInventoryItem> inventory;
	
	private Order failedorder;

	public Cart cart;

	private Map<ProductIdentifier, Integer> quantity;
	
	private final BusinessTime time;
	/**
	 * Initialisert den OrderController mit den notwendigen Attributen.
	 * @param orderManagement
	 * @param inventory
	 * @param userManagement
	 * @param time
	 */
	OrderController(OrderManagement<Order> orderManagement, UniqueInventory<UniqueInventoryItem> inventory, 
		UserManagement userManagement, BusinessTime time) {

		Assert.notNull(orderManagement, "OrderManagement must not be null.");
		Assert.notNull(inventory, "Inventory must not be null.");
		Assert.notNull(userManagement, "UserManagement must not be null!");
		Assert.notNull(time, "BusinessTime must not be null!");

		this.orderManagement = orderManagement;
		this.inventory = inventory;
		this.failedorder = null;
		this.quantity = new HashMap<ProductIdentifier, Integer>();
		this.userManagement = userManagement;
		this.cart = new Cart();
		this.time = time;
	}
	/**
	 * Setzt das cart dieser Klasse als ModelAttribut.
	 * @return {@link Cart} cart
	 */
	@ModelAttribute("cart")
	Cart initializeCart() {
		return cart;
	}
	/**
	 * Gibt das Cart zurück.
	 * @return {@link Cart} cart
	 */
	@Bean
	public Cart getCart() {
		return cart;
	}
	/**
	 * Gibt einen {@link Boolean} zurück ob sich Verschreibungspflichtige Medikamente im Warenkorb befinden.
	 * @param c
	 * @return {@link Boolean}
	 */
	public boolean haspresonly(Cart c){

		if(c.isEmpty()){

			return false;

		} 

		ArrayList<Boolean> ispresonly=new ArrayList<Boolean>();

		c.get().forEach(item -> ispresonly.add(((Medicine)item.getProduct()).isPresonly()));

		for(boolean b:ispresonly) {
			if(b){
				return true;
			}
		}

		return false;

	}
	/**
	 * Gibt eine Map {@link String}, {@link Integer} zurück. Die Strings repräsentieren die ID eines {@link CartItem}s
	 * Die Integer Werte die in Inventar verfügbare Menge der Produkte.
	 * @param cart
	 * @return HashMap<String, Integer>
	 */
	private HashMap<String, Integer> getAvailability(Cart cart){

		HashMap<String, Integer> ret = new HashMap<String, Integer>();

		cart.forEach(cartitem->{

			Medicine med = (Medicine) cartitem.getProduct();

			int quan=inventory.findByProduct(med).get().getQuantity().getAmount().intValue();

			ret.put(cartitem.getId(), quan);

		});

		return ret;
	}
	/**
	 * Fügt die verfügbarkeit aus {@link #getAvailability(Cart)} zum Model hinzu, wenn /cart aufgerufen wird.
	 * Leitet zu "cart" weiter.
	 * @param cart
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/cart")
	String basket(@ModelAttribute Cart cart, Model model) {

		model.addAttribute("availability", this.getAvailability(cart));

		return "cart";

	}
	/**
	 * Fügt ein Medikament {@link Medicine} mit einer Anzahl zum Warenkorb hinzu.
	 * Leitet auf Hauptseite weiter.
	 * @param item
	 * @param number
	 * @param cart
	 * @return der Name der Ansicht
	 */
	@PostMapping("/cart")
	String addItem(@RequestParam("pid") Medicine item, @RequestParam("number") int number, @ModelAttribute Cart cart) {
		
		int amount = number <= 0 ? 1 : number;
		
		cart.addOrUpdateItem(item, Quantity.of(amount));
		
		return "redirect:/";

	}
	/**
	 * Löscht ein Medikament aus dem Warenkorb und updated die Verfügbarkeit im Model.
	 * Leitet zu "cart" weiter.
	 * @param id
	 * @param cart
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/cart/{id}/delete")
	String deleteItem(@PathVariable String id, @ModelAttribute Cart cart, Model model) {

		cart.removeItem(id);

		model.addAttribute("availability", this.getAvailability(cart));

		return "cart";

	}

	/**
	 * Wenn "/updatecart" aufgerufen wird, dann leite zu "cart" weiter.
	 * Die Seite "updatecart" existiert nicht. Der Link dient nur zum registrieren der Mengenänderungen im Warenkorb.
	 * @return der Name der Ansicht
	 */
	@GetMapping("/updatecart")
	String basket2() {

		return "redirect:/cart";

	}
	/**
	 * Wird aufgerufen wenn man die Menge eines Produkts im Warenkorb ändert und auf Anpassen clickt.
	 * Ähnlich zu {@link #addItem(Medicine, int, Cart)}, jedoch wird hier zu "cart" weitergeleitet.
	 * @param item
	 * @param number
	 * @param cart
	 * @return der Name der Ansicht
	 */
	@PostMapping("/updatecart")
	String updateItem(@RequestParam("pid") String item, @RequestParam("number") int number, 
		@ModelAttribute Cart cart) {

		CartItem item2 = cart.getItem(item).get();

		int amount = number <= 0 ? 1 : number;

		cart.addOrUpdateItem(item2.getProduct(), Quantity.of(amount).subtract(item2.getQuantity()));

		return "redirect:/cart";

	}
	/**
	 * Dient zum löschen aller Produkte aus dem Warenkorb. Wird aufgerufen bei "Warenkorb leeren".
	 * Leitet zum Warenkorb zurück.
	 * @param cart
	 * @return der Name der Ansicht
	 */
	@PostMapping("/clearcart")
	String emptycart(@ModelAttribute Cart cart){

		cart.clear();

		return "redirect:/cart";

	}
	/**
	 * Wird aufgerufen, wenn man auf "checkout" clickt. Prüft ob alle notwendigen Daten für den User present sind via
	 * {@link CheckoutForm}.
	 * @param model
	 * @param checkoutForm
	 * @param cart
	 * @return der Name der Ansicht
	 */
	@GetMapping("/checkout")
	@PreAuthorize("isAuthenticated()")
	String checkout(Model model, CheckoutForm checkoutForm, Cart cart) {

		if (cart.isEmpty()) {

			return "redirect:/cart";

		}

		model.addAttribute("haspresonly", this.haspresonly(cart));
		model.addAttribute("cart", cart);
		model.addAttribute("checkoutForm", checkoutForm);
		model.addAttribute("user", userManagement.currentUser().get());
		
		return "checkout";
	}
	/**
	 * Diese Methode wird aufegrufen wenn man beim checkout auf "Jetzt Bezahlen" clickt.
	 * Sie Prüft ob alle notwendigen Angaben vorhanden sind für den eingeloggten User. Falls das nicht der Fall ist,
	 * wird man zum Warenkorb umgeleitet. Falls alle Eingaben stimmen wird man auf die Hauptseite weitergeleitet.
	 * @param model
	 * @param cart
	 * @param checkoutForm
	 * @param result
	 * @param userAccount
	 * @return der Name der Ansicht
	 */
	@PostMapping("/checkout")
	@PreAuthorize("isAuthenticated()")
	String buy(Model model, @ModelAttribute Cart cart, @Valid @ModelAttribute("checkoutForm")CheckoutForm checkoutForm, Errors result, @LoggedIn Optional<UserAccount> userAccount) {

		var user = userManagement.currentUser().get();

		model.addAttribute("haspresonly", this.haspresonly(cart));
		model.addAttribute("cart", cart);
		model.addAttribute("user", user);

		if (result.hasErrors()) {
			return "checkout";
		}
		
		return userAccount.map(account -> {

			PaymentMethod payment;
			var checkoutPayment = checkoutForm.getPayment();

			if(checkoutPayment.equals("payDirekt")) {
				payment = user.getPayDirekt();
			} else if (checkoutPayment.equals("bankAccount")) {
				payment = user.getBankAccount();
			} else if (checkoutPayment.equals("paymentCard")) {
				payment = user.getPaymentCard();
			} else {
				payment = Cash.CASH;
			}

			var order = new Order(account, payment);

			cart.addItemsTo(order);
			
			orderManagement.payOrder(order);

			userManagement.changeOrdered(user, true);

			cart.clear();

			return "redirect:/";

		}).orElse("redirect:/cart");

	}
	/**
	 * Ist zuständig für die Bestellungsübersicht für Mitarbeiter und Chef.
	 * Leitet nicht weiter. Fügt Liste von Bestellungen und einen {@link OrderFilter} zum Model hinzu.
	 * Sowie ein {@link Boolean} für die Arbeitszeit der Mitarbeiter.
	 * @param model
	 * @param userAccount
	 * @return der Name der Ansicht
	 */
	@GetMapping("/orders")
	String orders(Model model, @LoggedIn Optional<UserAccount> userAccount) {

		boolean itsBusinessTime = ((time.getTime().getHour() >= 6) && (time.getTime().getHour() < 20) && 
			(time.getTime().getDayOfWeek() != DayOfWeek.SUNDAY ));

		List<Order> ret = new ArrayList<>();

		if(!userAccount.isEmpty()){

			if(userAccount.get().hasRole(Role.of("BOSS"))){

				ret = this.orderManagement.findAll(Pageable.unpaged()).toList();

			}
			if(userAccount.get().hasRole(Role.of("CUSTOMER"))){

				ret = this.orderManagement.findBy(userAccount.get()).toList();

			}
			if(userAccount.get().hasRole(Role.of("EMPLOYEE"))){

				ret = this.orderManagement.findBy(OrderStatus.PAID).toList();

			}

		}

		model.addAttribute("TimeToDoBusiness", itsBusinessTime);
		model.addAttribute("rech",ret);
		model.addAttribute("filter", new OrderFilter());

		return "orders";

	}
	/**
	 * Wird beim Filtern aufgerufen. Der {@link OrderFilter} liefert den Enum-Typ nachdem gefiltert werden soll.
	 * Alle anderen Akteure die evtl durch zufall diese Methode ausführen bekommen demensprechend andere, ungefilterte
	 * Listen von {@link Order}s.
	 * @param filter
	 * @param model
	 * @param userAccount
	 * @return der Name der Ansicht
	 */
	@PostMapping("/orders")
	String postorders(@ModelAttribute OrderFilter filter,Model model, @LoggedIn Optional<UserAccount> userAccount) {

		List<Order> ret = new ArrayList<>();

		if(!userAccount.isEmpty()){

			if(userAccount.get().hasRole(Role.of("BOSS"))){

				switch(filter.getFilter()){

					case OFFEN: ret = this.orderManagement.findBy(OrderStatus.OPEN).toList();
					break;
					case BEZAHLT: ret = this.orderManagement.findBy(OrderStatus.PAID).toList();
					break;
					case COMPLETED: ret = this.orderManagement.findBy(OrderStatus.COMPLETED).toList();
					break;
					default: ret = this.orderManagement.findAll(Pageable.unpaged()).toList();
					break;

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
	/**
	 * Verwaltet die Ansicht der Rechnungsdetails. Findet die {@link Order} via dem {@link OrderIdentifier} und fügt
	 * sie dem Model hinzu. Leitet auf "orderdetails" weiter.
	 * @param id
	 * @param model
	 * @param userAccount
	 * @return der Name der Ansicht
	 */
	@GetMapping("/orders/{id}")
	public String detail(@PathVariable OrderIdentifier id, Model model, @LoggedIn Optional<UserAccount> userAccount) {
		
		if(userAccount.isEmpty()){

			model.addAttribute("rech", new ArrayList<Order>());
			model.addAttribute("filter", new OrderFilter());

			return "orders";

		}
		
		Order order = this.orderManagement.get(id).get();

		model.addAttribute("det", order);
			
		return "orderdetails";
	}
	/**
	 * Wird beim Fertigstellen einer Order aufgerufen. Falls die Bestellung über den Inventarbestand hinaus geht, 
	 * wird auf "ordercompletionfail" weitergeleitet. Dort lässt sich einsehen wieviel zu viel bestellt wurde.
	 * Falls das jedoch nicht der Fall sein sollte, wird die Bestellung fertiggestellt. Die Bestellung wird mittels
	 * {@link OrderIdentifier} gefunden.
	 * @param id
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/orders/{id}/complete")
	public String complete(@PathVariable OrderIdentifier id, Model model){

		if(this.orderManagement.get(id).isPresent()){

			Order order = this.orderManagement.get(id).get();

			List<OrderLine> orderlines = order.getOrderLines().toList();

			for(OrderLine orderline : orderlines){

				int quan = inventory.findByProductIdentifier(orderline.getProductIdentifier()).get().getQuantity()
					.getAmount().intValue();

				int orderd = orderline.getQuantity().getAmount().intValue();

				this.quantity.put(orderline.getProductIdentifier(), quan);

				if(!(quan >= orderd)){

					this.failedorder = order;

					return "redirect:/ordercompletionfail";

				}

			}

			this.orderManagement.completeOrder(order);
			
		}

		return "redirect:/orders";

	}
	/**
	 * Bei einem aufruf von "ordercompletionfail" wird die Globale {@link Order} welche fehlschlug, Fertigzustellen
	 * zum Model hinzugefügt. Zusätzlich wird die vorhandene Menge im Inventar hinzugefügt, um die 
	 * Fehlerquelle deutlich zu machen.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/ordercompletionfail")
	public String orderfail(Model model){
		
		model.addAttribute("order", this.failedorder);
		model.addAttribute("quantity", this.quantity);

		return "ordercompletionfail";
		
	}
	/**
	 * Wenn man in der Bestellübersicht auf "Abbrechen" clickt wird diese Mehtode aufgerufen.
	 * Sie Cancelled die {@link Order} im {@link OrderManagement} und leitet zu "orders" um.
	 * @param id
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/orders/{id}/cancel")
	public String cancel(@PathVariable OrderIdentifier id, Model model){

		if(this.orderManagement.get(id).isPresent()){

			try{
				Order order = this.orderManagement.get(id).get();
				this.orderManagement.cancelOrder(order, "Canceled by Boss");

			}catch(ClassCastException e){

				Order o=this.orderManagement.get(id).get();
				this.orderManagement.delete(o);

			}
		}

		return "redirect:/orders";

	}
	/**
	 * Wird aufgerufen, wenn man auf "Eigene Bestellungen" clickt.
	 * Findet alle Bestellungen des eingeloggten Users und fügt diese zum Model hinzu.
	 * @param model
	 * @param userAccount
	 * @return der Name der Ansicht
	 */
	@GetMapping("/myorders")
	String myorders(Model model, @LoggedIn Optional<UserAccount> userAccount) {

		List<Order> ret = new ArrayList<>();
		if(!userAccount.isEmpty()){

			ret=this.orderManagement.findBy(userAccount.get()).toList();

		}

		model.addAttribute("rech", ret);

		return "myorders";

	}
	
}
