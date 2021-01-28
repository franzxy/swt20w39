package pharmacy.finances;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.javamoney.moneta.Money;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.salespointframework.payment.PaymentMethod;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.salespointframework.accountancy.AccountancyEntry;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.time.Duration;

import pharmacy.AbstractIntegrationTests;
import pharmacy.finances.FilterForm.Filter;
import pharmacy.user.UserManagement;

@Transactional
public class AccountancyAdapterUnitTest extends AbstractIntegrationTests{

    @Autowired
    private Accountancy accountancy;

    @Autowired
    private UserAccountManagement userAccountManagement;

    @Autowired
    private BusinessTime businessTime;

    @Autowired
    private OrderManagement<Order> orderManagement;
    @Autowired
    private AccountancyAdapter accountancyAdapter;

    @Test   
    void nullTest(){
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> accountancyAdapter.add(null));
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> accountancyAdapter.setFix(null));
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> accountancyAdapter.findByUserAccount(null));
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> accountancyAdapter.getOrder(null));
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> accountancyAdapter.get(null));
        
    }
    Optional<AccountancyEntry> contains(HashMap<AccountancyEntry, LocalDate> map,String descriptionpart){
        for(Map.Entry<AccountancyEntry, LocalDate> entry : map.entrySet()){
            if(entry.getKey().getDescription().contains(descriptionpart)){
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
    @Test
    void fixFilterGetTest(){
        Fixcosts fix = new Fixcosts();
        
        fix.setRent(11);
        fix.setElectricity(12);
        fix.setHeating(13);
        fix.setWater(14);

        this.accountancyAdapter.setFix(fix);

        Fixcosts fix2 = this.accountancyAdapter.getFix();

        assertEquals(fix2, fix);

        this.businessTime.forward(Duration.ofDays(32));

        this.accountancyAdapter.createExamples();

        FilterForm f = new FilterForm();

        f.setFilter(Filter.ALLE);

        HashMap<AccountancyEntry, LocalDate> map = this.accountancyAdapter.filter(f);
       
        
        
        assertTrue( contains(map, "Stro").isPresent());
        assertTrue( contains(map, "Miet").isPresent());
        assertTrue( contains(map, "Wasse").isPresent());
        assertTrue( contains(map, "Heizkoste").isPresent());
        
        AccountancyEntry example = contains(map, "Test").get();

        Pair<AccountancyEntry, LocalDate> expair = this.accountancyAdapter.get(example.getId());
        assertTrue(expair.getValue0().equals(example));

        //Filter test

        f.setFilter(Filter.HEIZ);

        map = this.accountancyAdapter.filter(f);
       
        
        assertTrue( contains(map, "Stro").isEmpty());
        assertTrue( contains(map, "Miet").isEmpty());
        assertTrue( contains(map, "Wasse").isEmpty());
        assertTrue( contains(map, "Heizkoste").isPresent());

        f.setFilter(Filter.MIETE);

        map = this.accountancyAdapter.filter(f);
       
        
        assertTrue( contains(map, "Stro").isEmpty());
        assertTrue( contains(map, "Miet").isPresent());
        assertTrue( contains(map, "Wasse").isEmpty());
        assertTrue( contains(map, "Heizkoste").isEmpty());

        f.setFilter(Filter.OBEST);

        map = this.accountancyAdapter.filter(f);
       
        
        assertTrue( contains(map, "Stro").isEmpty());
        assertTrue( contains(map, "Miet").isEmpty());
        assertTrue( contains(map, "Wasse").isEmpty());
        assertTrue( contains(map, "Heizkoste").isEmpty());

        f.setFilter(Filter.STROM);

        map = this.accountancyAdapter.filter(f);
       
        
        assertTrue( contains(map, "Stro").isPresent());
        assertTrue( contains(map, "Miet").isEmpty());
        assertTrue( contains(map, "Wasse").isEmpty());
        assertTrue( contains(map, "Heizkoste").isEmpty());

        f.setFilter(Filter.WASSER);

        map = this.accountancyAdapter.filter(f);
       
        
        assertTrue( contains(map, "Stro").isEmpty());
        assertTrue( contains(map, "Miet").isEmpty());
        assertTrue( contains(map, "Wasse").isPresent());
        assertTrue( contains(map, "Heizkoste").isEmpty());

        assertTrue(this.accountancyAdapter.getRevenue().isEqualTo(Money.of(43, "EUR")));
        assertTrue(this.accountancyAdapter.getBalance().isEqualTo(Money.of(-3007, "EUR")));
        assertTrue(this.accountancyAdapter.getExpenses().isEqualTo(Money.of(-3050, "EUR")));

        UserAccount apo = this.userAccountManagement.findByUsername("apo").get();

        map = this.accountancyAdapter.findByUserAccount(apo);

        assertTrue( contains(map, "Rechnung Nr.").isPresent());

        AccountancyEntry orderentry = contains(map, "Rechnung Nr.").get();

        assertNotNull(this.accountancyAdapter.getOrder(orderentry.getId()));
        
       



    }
   

    
    
}
