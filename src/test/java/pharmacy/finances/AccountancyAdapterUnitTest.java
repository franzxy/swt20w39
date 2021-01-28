package pharmacy.finances;

import javax.transaction.Transactional;

import org.javamoney.moneta.Money;
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
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pharmacy.user.UserManagement;

@Transactional
public class AccountancyAdapterUnitTest {

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
        assertThatExceptionOfType(NullPointerException.class) //
                .isThrownBy(() -> accountancyAdapter.add(null));
        assertThatExceptionOfType(NullPointerException.class) //
                .isThrownBy(() -> accountancyAdapter.setFix(null));
        assertThatExceptionOfType(NullPointerException.class) //
                .isThrownBy(() -> accountancyAdapter.findByUserAccount(null));
        assertThatExceptionOfType(NullPointerException.class) //
                .isThrownBy(() -> accountancyAdapter.getOrder(null));
        assertThatExceptionOfType(NullPointerException.class) //
                .isThrownBy(() -> accountancyAdapter.get(null));
        
    }
    
    
}
