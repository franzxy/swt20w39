package pharmacy.finances;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.accountancy.AccountancyEntryIdentifier;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.salespointframework.useraccount.UserAccount;
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
	private LocalDateTime now;
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
		this.now=LocalDateTime.now();

				
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
		DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<AccountancyEntry> tmp=new ArrayList<AccountancyEntry>();
		final List<AccountancyEntry> ret=new ArrayList<AccountancyEntry>();
		switch(filter1) {
		case OBEST			: tmp = this.getEntriesOfRole("CUSTOMER");			break;
		case GEHÄLTER		: tmp = this.getByName("Gehalt von");				break;
		case STROM			: tmp = this.getByName("Strom");					break;
		case MIETE			: tmp = this.getByName("Miete");					break;
		case WASSER			: tmp = this.getByName("Wasser");					break;
		case HEIZ			: tmp = this.getByName("Heizkosten");				break;
		default				: tmp = this.acc.findAll().toList();				break;
		}
		if(filterB.isIntfilter()){
			try{
				LocalDate begin=LocalDate.parse(filterB.getBegin(), format);
				LocalDate end=LocalDate.parse(filterB.getEnd(), format);
				tmp.forEach(acc->{
					LocalDate l=acc.getDate().get().toLocalDate();
					if(l.isAfter(begin) && l.isBefore(end)){
						ret.add(acc);
					}
				});
			}catch(DateTimeParseException e){
				System.out.println("Failed to parse Time");
				return tmp;
			}
			return ret;
		}else{
			return tmp;
		}
		
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
	
	 //AutoPay helper #1
	private void createGehalt(){
		List<User> working=this.um.findAll().toList();
		for(User u:working) {
			if(u.getUserAccount().hasRole(Role.of("EMPLOYEE"))) {
				AccountancyEntry sal= new AccountancyEntry(u.getSalary().multiply(-1), "Gehalt von "+u.getUserAccount().getUsername());
				this.acc.add(sal);
			}
		}
	}
	//AutoPay helper #2
	private void createKosten(String bez, double betr){
		if(betr!=0){
			AccountancyEntry sal= new AccountancyEntry(Money.of(betr, "EUR"), bez);
			this.acc.add(sal);
		}
	}
	//Main AUTOPAY!
	private void autopay(){
		//Fixkosten
		createKosten("Strom", -1*this.fixk.getStrom());
		createKosten("Miete", -1*this.fixk.getMiete());
		createKosten("Wasser", -1*this.fixk.getWasser());
		createKosten("Heizkosten", -1*this.fixk.getHeizkosten());
		//Gehälter
		createGehalt();

	}

	@Scheduled(fixedRate = 500)
	protected void endofmonthdetector(){
		long months = time.getTime().getMonthValue() - now.getMonthValue();
        if(time.getTime().getYear()!=now.getYear()){
            months = (12 - now.getMonthValue()) + time.getTime().getMonthValue();
			months += 12 * (( time.getTime().getYear() - now.getYear()) - 1);
		}
		while(months>0){
			autopay();
			months--;
		}
		this.now=time.getTime();
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
	@GetMapping("/myfinances")
	public String myfinances(Model model, @LoggedIn Optional<UserAccount> userAccount) {
		List<AccountancyEntry> ret= new ArrayList<AccountancyEntry>();
		if(!userAccount.isEmpty()){
			this.acc.findAll().forEach(entry ->{
				this.orderManagement.findBy(userAccount.get()).forEach(order -> {
					if(entry.getDescription().contains(order.getId().getIdentifier())){
						ret.add(entry);
					 }
				});
				if(userAccount.get().hasRole(Role.of("EMPLOYEE"))){
					if(entry.getDescription().startsWith("Gehalt von ") && entry.getDescription().contains(userAccount.get().getUsername())){
						ret.add(entry);
					}
				}
				
			});
		}
		
		model.addAttribute("tab", ret);
		return "myfinances";
	}
	@GetMapping("/myfinances/{id}")
	public String salarypaper(@PathVariable AccountancyEntryIdentifier id,Model model) {
		model.addAttribute("det", this.acc.get(id).get());
		return "salarypaper";
	}

	@GetMapping("/finances/{id}")
	public String financedetail(@PathVariable String id,Model model) {
		String id2=id.replace("Rechnung Nr. ", "");
		this.orderManagement.findBy(OrderStatus.PAID).forEach(order->{
			if(order.getId().getIdentifier().equals(id2)){
				model.addAttribute("det", order);
			}
		});
		this.orderManagement.findBy(OrderStatus.COMPLETED).forEach(order->{
			if(order.getId().getIdentifier().equals(id2)){
				model.addAttribute("det", order);
			}
		});
		this.orderManagement.findBy(OrderStatus.OPEN).forEach(order->{
			if(order.getId().getIdentifier().equals(id2)){
				model.addAttribute("det", order);
			}
		});
		
		return "financedetails";
	}
	@GetMapping("/editfix")
	public String fix(Model model) {
		model.addAttribute("fixk",this.fixk);
		return "editfix";
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
			Order o1=new Order(this.userAccount.findByUsername("apo").get());
			o1.addChargeLine(Money.of(20,"EUR"), "default");
			o1.setPaymentMethod(Cash.CASH);
			Order o2=new Order(this.userAccount.findByUsername("apo").get());
			o2.addChargeLine(Money.of(20,"EUR"), "default");
			o2.setPaymentMethod(Cash.CASH);
			this.orderManagement.save(o1);
			this.orderManagement.payOrder(o1);
			this.orderManagement.save(o2);
		}
		this.acc.add(new AccountancyEntry(Money.of(23,"EUR")," Test"));
		this.autopay();
		return "redirect:/finances";
	}
}
