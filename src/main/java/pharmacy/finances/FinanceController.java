package pharmacy.finances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.accountancy.ProductPaymentEntry;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class FinanceController {
	@Autowired
	private final Accountancy acc;
	@Autowired
	private final OrderManagement<Order> orderManagement;
	@Autowired
	private final UserAccountManagement um;
	@Autowired
	private final Filter filter;
	@Autowired
	private final BusinessTime time;
	FinanceController(Filter filter,Accountancy acc, OrderManagement<Order> orderManagement, UserAccountManagement um,Filter f, BusinessTime time) {
		this.time = time;
		Assert.notNull(um, "OrderManagement must not be null!");
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(acc, "OrderManagement must not be null!");
		this.orderManagement = orderManagement;
		this.acc=acc;
		this.um=um;
		this.filter=filter;
		
		//BSP:
		
		//Warum??? laut doku passiert das Automatisch!!
		
		
	}

	@GetMapping("/finances")
	//@RequestMapping(value = "/finances", method = RequestMethod.GET)
	public String finances(Model model) {
		List<AccountancyEntry> ret=this.acc.findAll().toList();
		
		List<String> ret2=Arrays.asList("Online Bestellungen","Verkäufe","Praxis A","Praxis B","Praxis C","Gehälter","Strom","Miete","Wasser","Heizkosten");
		
		model.addAttribute("allTypes", ret2);
		model.addAttribute("filter", this.filter);
		model.addAttribute("tab", ret);
		
		System.out.println(this.filter.getFilterkriterium());
		return "finances";
	}
	
	
	@PostMapping("/filtern")
	 public String filtern(@ModelAttribute Filter filter,Model model) {
		 String result=filter.getFilterkriterium();
		 System.out.println(filter.getFilterkriterium());
		 List<AccountancyEntry> ret=new ArrayList<AccountancyEntry>();
		 
		 switch(result){
		 case "Online Bestellungen"	: ret = this.getEntriesOfRole("CUSTOMER");			break;
		 case "Verkäufe"			: ret = this.getEntriesOfRole("EMPLOYEE");			break;
		 case "Praxis A"			: ret = this.getEntriesOfRoleAndUser("DOCTOR", "A");break;
		 case "Praxis B"			: ret = this.getEntriesOfRoleAndUser("DOCTOR", "B");break;
		 case "Praxis C"			: ret = this.getEntriesOfRoleAndUser("DOCTOR", "C");break;
		 case "Gehälter"			: ret = this.createGehalt();						break;
		 case "Strom"				: ret = this.createKosten("Strom", 50);				break;
		 case "Miete"				: ret = this.createKosten("Miete",500);				break;
		 case "Wasser"				: ret = this.createKosten("Wasser", 20);			break;
		 case "Heizkosten"			: ret = this.createKosten("Heizkosten", 20);		break;
		 default					: ret = this.acc.findAll().toList();				break;
		 }
		 model.addAttribute("tab", ret);
		 return "finances";
	 }
	 private List<AccountancyEntry> getEntriesOfRole(String role){
		 List<AccountancyEntry> working=this.acc.findAll().toList();
		 List<AccountancyEntry> ret =new ArrayList<AccountancyEntry>();
		 for(Object o: this.orderManagement.findBy(OrderStatus.PAID).get().toArray()) {
			 for(AccountancyEntry a: working) {
				 if(a.getDescription().contains( ((Order) o).getId().toString())) {
					 if(((Order) o).getUserAccount().hasRole(Role.of(role))) {
					 ret.add(a);
					 }
					 
				 }
			 }
		 }
		 return ret;
	 }
	 private List<AccountancyEntry> getEntriesOfRoleAndUser (String role, String user){
		 List<AccountancyEntry> working=this.acc.findAll().toList();
		 List<AccountancyEntry> ret =new ArrayList<AccountancyEntry>();
		 for(Object o: this.orderManagement.findBy(OrderStatus.PAID).get().toArray()) {
			 for(AccountancyEntry a: working) {
				 if(a.getDescription().contains( ((Order) o).getId().toString())) {
					 if(((Order) o).getUserAccount().hasRole(Role.of(role))&& ((Order) o).getUserAccount().getLastname().equals(user)) {
					 ret.add(a);
					 }
					 
				 }
			 }
		 }
		 return ret;
	 }
	 private List<AccountancyEntry> createGehalt(){
		 List<AccountancyEntry> ret =new ArrayList<AccountancyEntry>();
		 List<UserAccount> working=this.um.findAll().toList();
		 if(this.time.getTime().getDayOfMonth()>29) {
		 for(UserAccount u:working) {
			 if(u.hasRole(Role.of("worker"))) {
				 Order order= new Order(u,Cash.CASH);
				 order.addChargeLine(Money.of(-900, "EUR"), "Gehalt für "+u.getLastname());
				 this.orderManagement.payOrder(order);
				 this.orderManagement.save(order);
				 for(AccountancyEntry a:this.acc.findAll().toList()) {
					 if(a.getDescription().contains(order.getId().toString())) {
						 ret.add(a);
					 }
				 }
			 }
		 }}
		 return ret;
	 }
	 private List<AccountancyEntry> createKosten(String bez, double betr){
		 List<AccountancyEntry> ret =new ArrayList<AccountancyEntry>();
		 if(this.time.getTime().getDayOfMonth()>29) {
			 List<UserAccount> working=this.um.findAll().toList();
			 for(UserAccount u:working) {
				 if(u.hasRole(Role.of("BOSS"))) {
					 Order order= new Order(u,Cash.CASH);
					 order.addChargeLine(Money.of(betr, "EUR"), bez);
					 this.acc.add(ProductPaymentEntry.of(order, bez));
					 for(AccountancyEntry a:this.acc.findAll().toList()) {
						 if(a.getDescription().contains(order.getId().toString())) {
							 ret.add(a);
						 }
					 }
				 }
			 }
		 }
		 return ret;
	 }
	 
	
}
