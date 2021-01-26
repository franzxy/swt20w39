package pharmacy.finances;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.javamoney.moneta.Money;
import org.javatuples.Pair;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.accountancy.AccountancyEntryIdentifier;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pharmacy.user.UserManagement;

@Service
@Transactional
public class AccountancyAdapter {

        @Autowired
        private final Accountancy accountancy;

        @Autowired
        private final UserManagement userManagement;

        @Autowired
        private final OrderManagement<Order> orderManagement;

        @Autowired
        private final BusinessTime businessTime;

        private final HashMap<AccountancyEntryIdentifier, LocalDate>  entries;

        private Fixcosts fixcosts;

        private final  DateTimeFormatter format;

        private final LocalDate init;

        public AccountancyAdapter(Accountancy accountancy, UserManagement userManagement, BusinessTime businessTime, 
                OrderManagement<Order> orderManagement) {
            
            Assert.notNull(accountancy, "Accountancy must not be null!");
            Assert.notNull(userManagement, "UserManagement must not be null!");
            Assert.notNull(orderManagement, "OrderManagement must not be null!");
            Assert.notNull(businessTime, "BusinessTime must not be null!");

            this.businessTime = businessTime;
            this.orderManagement = orderManagement;
            this.accountancy = accountancy;
            this.userManagement = userManagement;
            this.entries = new HashMap<>();
            this.init = LocalDate.now();
            this.fixcosts=new Fixcosts();
            this.format=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        }

        @Scheduled(fixedRate = 500)
        protected void autoPay(){

            LocalDate begin = this.getLastDate();
            LocalDate payday = LocalDate.of(begin.getYear(), begin.getMonthValue()+1, 1);
            LocalDate end = this.businessTime.getTime().toLocalDate();

            while(payday.isBefore(end)){

                this.createFixcosts(payday);
                this.createSalary(payday);

                payday = payday.plusMonths(1);

            }

        }

        private LocalDate getLastDate(){
            
            LocalDate newest = this.init;

            for(Map.Entry<AccountancyEntryIdentifier, LocalDate> entry : this.entries.entrySet()){

                newest = entry.getValue().isAfter(newest) ? entry.getValue() : newest;

            }

            return newest;

        }

        private void createSalary(LocalDate date){

            Assert.notNull(date, "Date must not be null!");

            this.userManagement.findAll().filter(user -> user.getUserAccount().hasRole(Role.of("EMPLOYEE")))
                .forEach(employee -> {

                    String userName = employee.getUserAccount().getUsername();

                    double amount = employee.getSalary().multiply(-1).getNumber().doubleValue();

                    this.createCosts(amount, "Gehalt von "+userName, date);

            });

        }

        private void createCosts(double amount, String description, LocalDate date){
            
            Assert.notNull(amount, "Amount must not be null!");
            Assert.notNull(description, "Description must not be null!");
            Assert.notNull(date, "Date must not be null!");

            BigDecimal cost = BigDecimal.valueOf(amount);

            if( cost.compareTo(BigDecimal.ZERO) != 0 ){

                AccountancyEntry entry = new AccountancyEntry(Money.of(amount, "EUR"), description);
                
                this.add(entry, date);

            }

        }

        private void createFixcosts(LocalDate date){

            Assert.notNull(date, "Date must not be null!");

            this.createCosts(this.fixcosts.getHeating()*(-1), "Heizkosten", date);
            this.createCosts(this.fixcosts.getRent()*(-1), "Miete", date);
            this.createCosts(this.fixcosts.getElectricity()*(-1), "Strom", date);
            this.createCosts(this.fixcosts.getWater()*(-1), "Wasser", date);
            
        }
        

        private List<AccountancyEntryIdentifier> filterByName(String name){

            Assert.notNull(name, "Name must not be null!");

            List<AccountancyEntryIdentifier> ret = new ArrayList<>();

            this.accountancy.findAll().forEach(entry -> {

                if(entry.getDescription().contains(name)){

                    ret.add(entry.getId());

                }

            });

            return ret;

        }

        private List<AccountancyEntryIdentifier> filterByRole(String role){

            Assert.notNull(role, "Role must not be null!");

            List<AccountancyEntryIdentifier> ret = new ArrayList<>();

            this.orderManagement.findAll(Pageable.unpaged()).forEach(order -> {
                
                if(order.getUserAccount().hasRole(Role.of(role))) {

                    ret.addAll(this.filterByName(order.getId().getIdentifier()));

                }

            });

            return ret;

        }

        private List<AccountancyEntryIdentifier> filterByInterval(LocalDate begin, LocalDate end, 
                List<AccountancyEntryIdentifier> list){
            
            Assert.notNull(begin, "Begin date must not be null!");
            Assert.notNull(end, "End date must not be null!");
            Assert.notNull(list, "List must not be null!");

            List<AccountancyEntryIdentifier> ret = new ArrayList<>();

            list.stream().forEach(id -> {

                LocalDate originalDate = this.accountancy.get(id).get().getDate().get().toLocalDate();
                LocalDate date = this.entries.containsKey(id) ? this.entries.get(id) : originalDate;

                if(date.isBefore(end) && date.isAfter(begin)){
                    
                    ret.add(id);

                }
            
            });

            return ret;

        }

        public HashMap<AccountancyEntry, LocalDate> idsToMap(List<AccountancyEntryIdentifier> list){

            Assert.notNull(list, "List must not be null!");

            HashMap<AccountancyEntry, LocalDate> ret = new HashMap<>();

            list.stream().forEach(id -> {

                LocalDate originalDate = this.accountancy.get(id).get().getDate().get().toLocalDate();
                LocalDate date = this.entries.containsKey(id) ? this.entries.get(id) : originalDate;

                ret.put(this.accountancy.get(id).get(), date);
            
            });

            return ret;
           
        }

        private void add(AccountancyEntry entry, LocalDate date){

            Assert.notNull(entry,"Entry must not be null!");
            Assert.notNull(date, "Date must not be null!");

            this.entries.put(entry.getId(), date);

            this.accountancy.add(entry);

        }

        public void add(AccountancyEntry entry){

            Assert.notNull(entry, "Entry must not be null!");

            this.add(entry, LocalDate.now());

        }

        public void setFix(Fixcosts fix){
            
            Assert.notNull(fix, "Fixcosts must not be null!");

            this.fixcosts = fix;

        }

        public HashMap<AccountancyEntry, LocalDate> findByUserAccount(UserAccount userAccount){

            Assert.notNull(userAccount, "UserAccount must not be null!");

            List<AccountancyEntryIdentifier> ret = new ArrayList<>();

            this.orderManagement.findBy(userAccount).forEach(order -> {
                 
                ret.addAll(this.filterByName(order.getId().getIdentifier()));

            });

            if(userAccount.hasRole(Role.of("EMPLOYEE"))){

                ret.addAll(this.filterByName("Gehalt von "+userAccount.getUsername()));

            }

            return this.idsToMap(ret);

        }

        public Order getOrder(AccountancyEntryIdentifier id){

            Assert.notNull(id, "ID must not be null!");

            AccountancyEntry entry = this.accountancy.get(id).get();

            String orderId = entry.getDescription().replace("Rechnung Nr. ", "");

            List<Order> orders = this.orderManagement.findAll(Pageable.unpaged()).toList();

            for(Order o : orders){

                if(o.getId().getIdentifier().equals(orderId)){

                    return o;

                } 
            
            }

            return null;

        }

        public HashMap<AccountancyEntry, LocalDate> filter(FilterForm filterForm){

            Assert.notNull(filterForm, "FilterForm must not be null!");

            List<AccountancyEntryIdentifier> ret = new ArrayList<>();
            
            //Filter by Categories
            switch(filterForm.getFilter()) {

                case OBEST			: ret = this.filterByRole("CUSTOMER");			                            
                break;
                case GEHÄLTER		: ret = this.filterByName("Gehalt von");				                    
                break;
                case STROM			: ret = this.filterByName("Strom");					                        
                break;
                case MIETE			: ret = this.filterByName("Miete");					                        
                break;
                case WASSER			: ret = this.filterByName("Wasser");					                    
                break;
                case HEIZ			: ret = this.filterByName("Heizkosten");				                    
                break;
                default				: ret = this.accountancy.findAll().map(entry -> entry.getId()).toList();    
                break;
    
            }

            //Filter by Interval
            if(filterForm.isIntfilter()){

                LocalDate begin = LocalDate.parse(filterForm.getBegin(), this.format);
                LocalDate end = LocalDate.parse(filterForm.getEnd(), this.format);

                if(begin.isAfter(end)){

                    throw new DateTimeParseException("begin is after end", filterForm.getBegin()+" "
                            +filterForm.getEnd(), 0);
                
                } 
                
                return this.idsToMap(this.filterByInterval(begin, end, ret));

            }
            
            return this.idsToMap(ret);

        } 

        public Money getRevenue(){

            Money ret = Money.of(0.0, "EUR");

            for(AccountancyEntry entry : this.accountancy.findAll().toList()){

                if(entry.isRevenue()){

                    ret = ret.add(entry.getValue());

                } 
            
            }
            
            return ret;

        }

        public Money getExpenses(){

            Money ret = Money.of(0.0, "EUR");

            for(AccountancyEntry entry : this.accountancy.findAll().toList()){

                if(entry.isExpense()){

                    ret = ret.add(entry.getValue());

                } 
            
            }

            return ret;

        }

        public Money getBalance(){
            
            Money ret = Money.of(0.0, "EUR");

            for(AccountancyEntry entry : this.accountancy.findAll().toList()){

                ret = ret.add(entry.getValue());
            
            }
            
            return ret;

        }

        public Fixcosts getFix(){

            return this.fixcosts;
        
        }

        public Pair<AccountancyEntry, LocalDate> get(AccountancyEntryIdentifier id){

            Assert.notNull(id, "ID must not be null!");

            AccountancyEntry entry = this.accountancy.get(id).get();

            LocalDate date = this.entries.containsKey(id) ? this.entries.get(id) : entry.getDate().get().toLocalDate();
            
            return new Pair<AccountancyEntry, LocalDate>(entry, date);

        }

        public void createExamples(){

            UserAccount apo = this.userManagement.findAll().filter(user -> {
                
               return user.getUserAccount().hasRole(Role.of("BOSS"));

            }).stream().findFirst().get().getUserAccount();

            Order o1=new Order(apo);
            o1.addChargeLine(Money.of(20,"EUR"), "default");
            o1.setPaymentMethod(Cash.CASH);
        
            this.orderManagement.save(o1);
            this.orderManagement.payOrder(o1);

            Order o2=new Order(apo);
            o2.addChargeLine(Money.of(20,"EUR"), "default");
            o2.setPaymentMethod(Cash.CASH);

            this.orderManagement.save(o2);

            this.add(new AccountancyEntry(Money.of(23,"EUR")," Test"));

        }
    
}
