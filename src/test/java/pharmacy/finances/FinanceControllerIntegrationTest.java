package pharmacy.finances;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import pharmacy.AbstractIntegrationTests;


public class FinanceControllerIntegrationTest  extends AbstractIntegrationTests {


    @Autowired 
    FinanceController controller;

    @Test
    @WithMockUser(roles = "BOSS")
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
    void nullContructor() {
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> new FinanceController(null));
    }


}