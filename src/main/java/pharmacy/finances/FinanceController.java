package pharmacy.finances;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pharmacy.user.User;
import pharmacy.user.UserManagement;

@Controller
@org.springframework.core.annotation.Order(20)
@EnableScheduling
public class FinanceController {
	@Autowired
	private final Accountancy acc;
	@Autowired
	private final OrderManagement<Order> orderManagement;

	private final UserManagement um;
	@Autowired
	private final BusinessTime time;
	@Autowired
	private final UserAccountManagement userAccount;
	private Money ist;
	private Money plus;
	private Money minus;
	private Fixkosten fixk;
	
	FinanceController(Accountancy acc, OrderManagement<Order> orderManagement, UserManagement um, BusinessTime time, 
			UserAccountManagement userAccount) {
		this.userAccount=userAccount;
		this.time = time;
		Assert.notNull(um, "OrderManagement must not be null!");
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(acc, "OrderManagement must not be null!");
		this.orderManagement = orderManagement;
		this.acc=acc;
		this.um=um;
		this.ist=Money.of(0.0, "EUR");
		this.minus=Money.of(0.0, "EUR");
		this.plus=Money.of(0.0, "EUR");
		this.fixk=new Fixkosten();
				
	}
	//Filter Stuff #1
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
	//Filter Stuff #2
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
	//Filter Stuff #3
	private List<AccountancyEntry> getByName(String name){
		List<AccountancyEntry> ret=new ArrayList<AccountancyEntry>();
		for(AccountancyEntry a:this.acc.findAll().toList()){
			if(a.getDescription().contains(name)){
				ret.add(a);
			}
		}
		return ret;
	}
	//Main Filter Thing
	private List<AccountancyEntry> filter(FilterForm filterB){
		FilterForm.Filter filter1=filterB.getFilter();
		List<AccountancyEntry> ret=new ArrayList<AccountancyEntry>();
		switch(filter1) {
		case OBEST			: ret = this.getEntriesOfRole("CUSTOMER");			break;
		case VERK			: ret = this.getEntriesOfRole("EMPLOYEE");			break;
		case PRAXA			: ret = this.getEntriesOfRoleAndUser("DOCTOR", "A");break;
		case PRAXB			: ret = this.getEntriesOfRoleAndUser("DOCTOR", "B");break;
		case PRAXC			: ret = this.getEntriesOfRoleAndUser("DOCTOR", "C");break;
		case GEHÄLTER		: ret = this.getByName("Gehalt von");				break;
		case STROM			: ret = this.getByName("Strom");					break;
		case MIETE			: ret = this.getByName("Miete");					break;
		case WASSER			: ret = this.getByName("Wasser");					break;
		case HEIZ			: ret = this.getByName("Heizkosten");				break;
		default				: ret = this.acc.findAll().toList();				break;
		}
		return ret;
	}
	private void updateMoney(){
		this.ist=Money.of(0.0, "EUR");
		this.minus=Money.of(0.0, "EUR");
		this.plus=Money.of(0.0, "EUR");
		for(AccountancyEntry a: this.acc.findAll().toList()) {
			this.ist=this.ist.add(a.getValue());
			if(a.isRevenue())this.plus=this.plus.add(a.getValue());
			if(a.isExpense())this.minus=this.minus.add(a.getValue());
		}
	}
	
	 //AutoPay
	private void createGehalt(){
		List<User> working=this.um.findAll().toList();
		if(this.time.getTime().getDayOfMonth()>29) {
		for(User u:working) {
			if(u.getUserAccount().hasRole(Role.of("EMPLOYEE"))) {
				AccountancyEntry sal= new AccountancyEntry(u.getSalary(), "Gehalt von "+u.getUserAccount().getLastname());
				this.acc.add(sal);
			}
		}}
	}
	private void createKosten(String bez, double betr){
		AccountancyEntry sal= new AccountancyEntry(Money.of(betr, "EUR"), bez);
		this.acc.add(sal);
	}
	@Scheduled(cron="0 0 0 1 * ?")
	protected void autopay(){
		//Fixkosten
		createKosten("Strom", -1*this.fixk.getStrom());
		createKosten("Miete", -1*this.fixk.getMiete());
		createKosten("Wasser", -1*this.fixk.getWasser());
		createKosten("Heizkosten", -1*this.fixk.getHeizkosten());
		//Gehälter
		createGehalt();

	}
	
	@GetMapping("/finances")
	@PreAuthorize("hasRole('BOSS')")
	public String finances(Model model) {
		this.updateMoney();
		model.addAttribute("filterB",new FilterForm());
		model.addAttribute("tab", this.acc.findAll().toList());
		model.addAttribute("rech", this.orderManagement.findBy(OrderStatus.OPEN).toList());
		model.addAttribute("plus",this.plus.getNumber().doubleValue());
		model.addAttribute("minus",this.minus.getNumber().doubleValue());
		model.addAttribute("fixk",this.fixk);
		model.addAttribute("total", this.ist.getNumber().doubleValue());
		
		
		return "finances";
	}
	
	@PostMapping("/finances")
	@PreAuthorize("hasRole('BOSS')")
	public String financesupdate(@ModelAttribute FilterForm filterB, Model model) {
		model.addAttribute("filterB", filterB);
		model.addAttribute("rech", this.orderManagement.findBy(OrderStatus.OPEN).toList());
		model.addAttribute("plus",this.plus.getNumber().doubleValue());
		model.addAttribute("minus",this.minus.getNumber().doubleValue());
		model.addAttribute("fixk",this.fixk);
		model.addAttribute("total", this.ist.getNumber().doubleValue());
		model.addAttribute("tab", this.filter(filterB));
		
		return "finances";
	}
	@GetMapping("/editfix")
	public String fix(Model model) {
		return "redirect:/finances#editfix";
	}
	@PostMapping("/editfix")
	public String fixsave(@ModelAttribute Fixkosten fixk,Model model) {
		this.fixk=fixk;
		model.addAttribute("fixk",fixk);
		return "redirect:/finances";
	}
	
	@GetMapping("/create")
	public String hallo(Model model) {
		return "redirect:/finances";
	}
	@PostMapping("/create")
	public String createdefaultenties( Model model) {
		if(!this.userAccount.findAll().isEmpty()){
			//System.out.println(this.userAccount.findByUsername("boss").get());
			Order o1=new Order(this.userAccount.findByUsername("boss").get());
			o1.addChargeLine(Money.of(20,"EUR"), "default");
			o1.setPaymentMethod(Cash.CASH);
			Order o2=new Order(this.userAccount.findByUsername("boss").get());
			o2.addChargeLine(Money.of(20,"EUR"), "default");
			o2.setPaymentMethod(Cash.CASH);
			this.orderManagement.save(o1);
			this.orderManagement.payOrder(o1);
			this.orderManagement.save(o2);
		}
		this.acc.add(new AccountancyEntry(Money.of(23,"EUR")," Test"));
		this.autopay();
		this.createGehalt();
		return "redirect:/finances";
	}


	

	 
	
}
