package pharmacy.inventory;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.AbstractErrors;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import pharmacy.AbstractIntegrationTests;
import pharmacy.catalog.MedicineCatalog;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	private InventoryController inventoryController;

	@Autowired
	private UserAccountManagement userAccountManagement;

	@Autowired
	private MedicineCatalog medicineCatalog;

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	
	
	@Test
    void nullContructor() {
        assertThatExceptionOfType(IllegalArgumentException.class) //
                .isThrownBy(() -> new InventoryController(null,null,null,null,null,null));
    
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void inventoryTest(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.inventory(model);
		assertEquals(result, "inventory");
		//Model Auswerten
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void inventoryTest1(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.filtern(model);
		assertEquals(result, "inventory");
		//Model auswerten
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void addmedTest(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.premed(model);
		assertEquals(result, "redirect:/inventory");
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void addmedTest1(){

		Errors err = new ErrorTest();
		Model model = new ExtendedModelMap();
		MedicineForm form = new MedicineForm();
		form.setName("xy");
		form.setTags("tag, tAAAGG");
		String result = this.inventoryController.addingMedicine(form, err, model);
		assertEquals(result, "redirect:/inventory");
		//Model Auswerten
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void increaseQuantityTest(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.preinreaseQuantity(model);
		assertEquals(result, "redirect:/inventory");
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void increaseQunatityTest1(){

		Model model = new ExtendedModelMap();
		MedicineForm form = new MedicineForm();
		String id = this.inventory.findAll().stream().findFirst().get().getId().getIdentifier();
		form.setId(id);
		String result = this.inventoryController.inreaseQuantity(form, model);
		assertEquals(result, "redirect:/inventory");
		//Model Auswerten
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void deleteTest(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.predelete( model);
		assertEquals(result, "redirect:/inventory");
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void deleteTest1(){

		Model model = new ExtendedModelMap();
		MedicineForm form = new MedicineForm();
		String id = this.inventory.findAll().stream().findFirst().get().getId().getIdentifier();
		form.setId(id);
		String result = this.inventoryController.delete(form, model);
		assertEquals(result, "redirect:/inventory");
		//Model Auswerten
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void detailTest(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.predetails( model);
		assertEquals(result, "meddetail");
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void detailTest1(){
		Model model = new ExtendedModelMap();
		MedicineForm form = new MedicineForm();String id = this.inventory.findAll().stream().findFirst().get().getId().getIdentifier();
		form.setId(id);
		String result = this.inventoryController.details(form, model);
		assertEquals(result, "meddetail");

		model = new ExtendedModelMap();
		result = this.inventoryController.details(form, model);
		assertEquals(result, "meddetail");
		//Model auswerten
	}
	@Test
	@WithMockUser(roles = "BOSS")
	void descardTest(){
		Model model = new ExtendedModelMap();
		String result = this.inventoryController.flush( model);
		assertEquals(result, "redirect:/inventory");
		assertEquals(model.asMap().get("formular").getClass().getSimpleName(), "MedicineForm");
	}
	

	
}
