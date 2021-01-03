package pharmacy.inventory;

import org.javamoney.moneta.Money;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;




// Straight forward?

@Controller
class InventoryController {
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	@Autowired
	private MedicineCatalog medicineCatalog;
	@Autowired
	private final UserAccountManagement userAccount;
	@Autowired
	private final OrderManagement<Order> orderManagement;

	private MedicineForm formular;

	
	InventoryController(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog, UserAccountManagement userAccount, OrderManagement<Order> orderManagement) {
		this.inventory = inventory;
		this.medicineCatalog=medicineCatalog;
		this.userAccount=userAccount;
		this.orderManagement=orderManagement;
		this.formular=new MedicineForm();
	}
	@Scheduled(cron="0 0 0 1 * ? *")
	private void autorestock(){
		this.inventory.findAll().forEach(item->{
			if(!item.hasSufficientQuantity(Quantity.of(((Medicine)item.getProduct()).getQuantity()))){
				Order o1 = new Order(this.userAccount.findByUsername("boss").get());
				while(item.getQuantity().isLessThan(Quantity.of(((Medicine)item.getProduct()).getQuantity()))){
					o1.addChargeLine(((Medicine)item.getProduct()).getPurchaseprice().multiply(-1), "Nachbestellung von "+((Medicine)item.getProduct()).getName());
					item.increaseQuantity(Quantity.of(1));
				}
				o1.setPaymentMethod(Cash.CASH);
				this.orderManagement.save(o1);
				this.orderManagement.payOrder(o1);
			}
		});
	}
	/**
	 * Displays all {@link InventoryItem}s in the system
	 *
	 * @param model will never be {@literal null}.
	 * @return the view name.
	 */
	private void restockByOne(String id){
		this.inventory.findAll().forEach(item->{
			if(item.getId().getIdentifier().equals(id)){
				Order o1 = new Order(this.userAccount.findByUsername("boss").get());
				o1.addChargeLine(((Medicine)item.getProduct()).getPurchaseprice().multiply(-1), "Manuelle Nachbestellung von "+((Medicine)item.getProduct()).getName());
				item.increaseQuantity(Quantity.of(1));
				o1.setPaymentMethod(Cash.CASH);
				this.orderManagement.save(o1);
				this.orderManagement.payOrder(o1);
			}
		});
	}
	
	@GetMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String inventory(Model model) {
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", this.formular);
		return "inventory";
	}
	@PostMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String filtern( Model model) {
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", this.formular);
		return "inventory";
	}



	@GetMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String premed(Model model) {
		//model.addAttribute("formular", this.formular);
		return "redirect:/inventory";
	}
	@PostMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String addingMedicine(@Valid MedicineForm formular, Errors result, Model model) {
		if(result.hasErrors()){
			this.formular=formular;
			model.addAttribute("formular", this.formular);
			return "redirect:/inventory#newmed";
		}
		System.out.println("ID: "+formular.getId());
		this.formular=formular;
		if(!this.formular.getId().equals("")){
			UniqueInventory<UniqueInventoryItem> tmp= this.inventory;
			tmp.findAll().forEach(item-> {
			if(item.getId().getIdentifier().equals(formular.getId())){
				this.inventory.delete(item);
				System.out.println("Medikament gelöscht");
			}
			});
			MedicineCatalog tmp2=this.medicineCatalog;
			tmp2.findAll().forEach(med->{
				if(!this.inventory.findByProduct(med).isEmpty()){
					if(this.inventory.findByProduct(med).get().getId().getIdentifier().equals(formular.getId())){
						this.medicineCatalog.delete(med);
					}
				}
			});
		}
		medicineCatalog.save(formular.toMedicine());
		//Copied that from initializer, only works that way. Why ever
		medicineCatalog.findAll().forEach(medicine -> {
			// Try to find an InventoryItem for the project and create a default one with 10 items if none available
			if (inventory.findByProduct(medicine).isEmpty()) {
				inventory.save(new UniqueInventoryItem((Medicine)medicine, Quantity.of(formular.getQuantity())));
			}});
		
		this.formular=new MedicineForm();
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", this.formular);
		return "redirect:/inventory";
	}


	
	@GetMapping("/inrease")
	@PreAuthorize("hasRole('BOSS')")
	String preinreaseQuantity(Model model) {
		return "redirect:/inventory#newmed";
	}
	@PostMapping("/increase")
	@PreAuthorize("hasRole('BOSS')")
	String inreaseQuantity(@ModelAttribute MedicineForm formular, Model model) {
	
		this.restockByOne(formular.getId());
		this.formular=new MedicineForm();
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", this.formular);
		return "redirect:/addmed";
	}



	@GetMapping("/delete")
	@PreAuthorize("hasRole('BOSS')")
	String predelete(Model model) {
		return "redirect:/inventory#newmed";
	}
	@PostMapping("/delete")
	@PreAuthorize("hasRole('BOSS')")
	String delete(@ModelAttribute MedicineForm formular, Model model) {
		UniqueInventory<UniqueInventoryItem> tmp= this.inventory;
		tmp.findAll().forEach(item-> {
			if(item.getId().getIdentifier().equals(formular.getId())){
				this.inventory.delete(item);
			}
		});
		this.formular=new MedicineForm();
		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", this.formular);
		return "redirect:/addmed";
	}




	@GetMapping("/details")
	@PreAuthorize("hasRole('BOSS')")
	String predetails(Model model) {
		//model.addAttribute("formular", new MedicineForm());
		return "redirect:/inventory#newmed";
	}
	@PostMapping("/details")
	@PreAuthorize("hasRole('BOSS')")
	String details(@ModelAttribute MedicineForm formular, Model model) {
		System.out.println("ID for details "+formular.getId());
		MedicineForm med= new MedicineForm();
		this.inventory.findAll().forEach(item->{
			if(item.getId().getIdentifier().equals(formular.getId())){
				Medicine m=(Medicine)item.getProduct();
				med.setId(formular.getId());
				med.setAmount(m.getAmount());
				med.setDescription(m.getDescription());
				med.setImage(m.getImage());
				med.setName(m.getName());
				med.setPresonly(m.isPresonly());
				med.setPrice(m.getPrice().getNumber().doubleValue());
				med.setPurchasingprice(m.getPurchaseprice().getNumber().doubleValue());
				med.setQuantity(item.getQuantity().getAmount().intValue());
				med.setTags(String.join(",", m.getCategories().toList()));
			}
		});
		this.formular=med;
		model.addAttribute("formular",med);
		model.addAttribute("inventory", inventory.findAll().toList());
		
		return "redirect:/inventory#newmed";
	}
}

