package pharmacy.finances;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import pharmacy.AbstractIntegrationTests;


public class FinanceControllerIntegrationTest  extends AbstractIntegrationTests {

    @Autowired
    private UserAccountManagement userAccountManagement;
    
    @Autowired
    private AccountancyAdapter accountancyAdapter;

    
    @Autowired 
    FinanceController controller;

    @Test
    @WithMockUser(roles = "BOSS")
	public void controllerIntegrationTest10() {

		Model model = new ExtendedModelMap();

		String returnedView = controller.finances( model);

		assertEquals(returnedView,"finances");

		Object object = model.asMap().get("filterForm");
        
        assertTrue(object.getClass().getSimpleName().equals("FilterForm"));
        
        object = model.asMap().get("fixcosts");
        
        assertTrue(object.getClass().getSimpleName().equals("Fixcosts"));

        object = model.asMap().get("fail");
        
        assertFalse((Boolean)object);

        object = model.asMap().get("total");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("minus");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("plus");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("tab");
        
        assertEquals(Collections.emptyMap(), object);


    }
    @Test
    @WithMockUser(roles = "BOSS")
	public void controllerIntegrationTest11() {
        Model model = new ExtendedModelMap();
        FilterForm f = new FilterForm();
        String returnedView = this.controller.financesupdate(f, model);

        assertEquals("finances", returnedView);
        Object object = model.asMap().get("filterForm");
        
        assertTrue(object.getClass().getSimpleName().equals("FilterForm"));
        
        object = model.asMap().get("fixcosts");
        
        assertTrue(object.getClass().getSimpleName().equals("Fixcosts"));

        object = model.asMap().get("fail");
        
        assertFalse((Boolean)object);

        object = model.asMap().get("total");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("minus");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("plus");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("tab");
        
        assertEquals(Collections.emptyMap(), object);
    
    }
    @Test
    @WithMockUser(roles = "BOSS")
	public void controllerIntegrationTest12() {
        Model model = new ExtendedModelMap();
        FilterForm f = new FilterForm();
        f.setBegin("bla");
        f.setEnd("blub");
        f.setIntfilter(true);
        String returnedView = this.controller.financesupdate(f, model);

        assertEquals("finances", returnedView);
        Object object = model.asMap().get("filterForm");
        
        assertTrue(object.getClass().getSimpleName().equals("FilterForm"));
        
        object = model.asMap().get("fixcosts");
        
        assertTrue(object.getClass().getSimpleName().equals("Fixcosts"));

        object = model.asMap().get("fail");
        
        assertTrue((Boolean)object);

        object = model.asMap().get("total");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("minus");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("plus");
        
        assertTrue((Double) object == 0.0);

        object = model.asMap().get("tab");
        
        assertEquals(Collections.emptyMap(), object);
    
    }
    @Test
    void controllerIntegrationTest20() {
        Model model = new ExtendedModelMap();
        Optional<UserAccount> user = Optional.empty();
        String returnedView = this.controller.myfinances( model, user );
        assertEquals(returnedView, "myfinances");
        assertEquals(model.asMap().get("tab"),Collections.emptyMap() ); 
        user = this.userAccountManagement.findByUsername("apo");
        assertTrue(user.isPresent());
        assertEquals(model.asMap().get("tab"), Collections.emptyMap());


    }
    @Test
    void controllerIntegrationTest30() {
        Model model = new ExtendedModelMap();
        this.accountancyAdapter.createExamples();
        AccountancyEntry entry =  this.accountancyAdapter.findByUserAccount(
            this.userAccountManagement.findByUsername("apo").get()).keySet().stream().findFirst().get();

        String returnedView = this.controller.salarypaper(entry.getId(), model );
        assertEquals(model.asMap().get("det").getClass().getSimpleName(), "Pair");

        assertEquals(returnedView, "salarypaper");

    }
    @Test
    void controllerIntegrationTest40() {
        Model model = new ExtendedModelMap();
        this.accountancyAdapter.createExamples();
        AccountancyEntry entry =  this.accountancyAdapter.findByUserAccount(
            this.userAccountManagement.findByUsername("apo").get()).keySet().stream().findFirst().get();

        String returnedView = this.controller.financedetail(entry.getId(), model );
        assertEquals(model.asMap().get("det").getClass().getSimpleName(), "Order");

        assertEquals(returnedView, "financedetails");

    }
    @Test
    void controllerIntegrationTest50() {
        Model model = new ExtendedModelMap();
        
        String returnedView = this.controller.fix( model );
        assertEquals(model.asMap().get("fixcosts").getClass().getSimpleName(), "Fixcosts");

        assertEquals(returnedView, "editfix");

    }
    @Test
    void controllerIntegrationTest51() {
        Model model = new ExtendedModelMap();
        Fixcosts f = new Fixcosts();
        String returnedView = this.controller.fixsave(f, model );
        assertEquals(model.asMap().get("fixcosts").getClass().getSimpleName(), "Fixcosts");
        assertEquals(f, this.accountancyAdapter.getFix());
        assertEquals(returnedView, "redirect:/finances");

    }
    @Test
    void controllerIntegrationTest60() {
        Model model = new ExtendedModelMap();
        String returnedView = this.controller.hallo(model);
        assertEquals(returnedView, "redirect:/finances");

    }
    @Test
    void controllerIntegrationTest61() {
        Model model = new ExtendedModelMap();
        String returnedView = this.controller.createdefaultenties(model);
        assertEquals(returnedView, "redirect:/finances");
        assertTrue(this.accountancyAdapter.getBalance().isEqualTo(Money.of(43, "EUR")));
    }




    @Test
    void nullContructor() {
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> new FinanceController(null));
    }


}