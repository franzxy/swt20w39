package pharmacy.inventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;

@Controller
@EnableScheduling
class InventoryController {

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	@Autowired
	private MedicineCatalog medicineCatalog;

	@Autowired
	private final UserAccountManagement userAccount;

	@Autowired
	private final OrderManagement<Order> orderManagement;

	@Autowired
	private BusinessTime time;
	
	private LocalDateTime now;

	@Autowired
	private Map<String, Integer> waitlist;

	
	InventoryController(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog, 
			UserAccountManagement userAccount, OrderManagement<Order> orderManagement, BusinessTime time, 
			Map<String, Integer> waitlist) {

		this.inventory = inventory;
		this.medicineCatalog=medicineCatalog;
		this.userAccount=userAccount;
		this.orderManagement=orderManagement;
		this.waitlist=waitlist;
		this.time=time;
		this.now=LocalDateTime.now();
		
	}
	
	private void autorestock(){

		this.inventory.findAll().forEach(item->{

			if(!item.hasSufficientQuantity(Quantity.of(((Medicine)item.getProduct()).getQuantity()))){

				int anz = ((Medicine)item.getProduct()).getQuantity() - item.getQuantity().getAmount().intValue(); 

				this.restock(anz, item.getId().getIdentifier());

			}

		});

		this.waitlist.forEach((k,v) -> {

			restock(v, k);

		});

		this.waitlist=new HashMap<String, Integer>();

	}

	private UniqueInventoryItem findById(String id){

		return this.inventory.findAll().filter(item ->{

			return item.getId().getIdentifier().equals(id);

		}).stream().findFirst().get();

	}

	@Scheduled(fixedRate = 500)
	protected void endofdaydetector(){

		LocalDate begin = now.toLocalDate();
		LocalDate end = time.getTime().toLocalDate();

		while(begin.isBefore(end)){
			
			autorestock();

			begin = begin.plusDays(1);

		}

		this.now=time.getTime();

	}

	private void restock(int anz, String id){

		UniqueInventoryItem item = this.findById(id);

		Medicine med = (Medicine) item.getProduct();

		inventory.save(item.increaseQuantity(Quantity.of(anz)));
		
		Order o1 = new Order(this.userAccount.findByUsername("apo").get());

		o1.addChargeLine(med.getPurchaseprice().multiply(-1F*anz), "Nachbestellung von: "+anz+"x "+med.getName());
		o1.setPaymentMethod(Cash.CASH);

		this.orderManagement.save(o1);
		this.orderManagement.payOrder(o1);
		this.orderManagement.completeOrder(o1);
	
	}
	
	@GetMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String inventory(Model model) {

		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());
		model.addAttribute("waitlist", this.waitlist);

		return "inventory";
	
	}

	@PostMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String filtern( Model model) {

		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());
		model.addAttribute("waitlist", this.waitlist);

		return "inventory";

	}

	@GetMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String premed(Model model) {
		
		return "redirect:/inventory";

	}

	//When adding, a new medicine is created, the target quantity is defined in the medicine itself, 
	//but there is none in the inventory yet.
	//Since the target quantity is set in the medicine, the autorestock method will stop there and automatically 
	//reorder it.
	//If only one existing medicine is being processed, it will be deleted before adding it, otherwise a duplicate 
	//will be created.
	//Important: The ID changes after editing!
	@PostMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String addingMedicine(@Valid @ModelAttribute("formular")MedicineForm formular, Errors result, Model model) {
		
		if(result.hasErrors()){
			
			return "meddetail";
		}

		int quantity = 0;

		Medicine med = formular.toMedicine();
	
		if(!formular.getId().equals("")){

			UniqueInventoryItem item = this.findById(formular.getId());

			quantity = item.getQuantity().getAmount().intValue();

			this.inventory.delete(item);
	
			this.medicineCatalog.delete((Medicine)item.getProduct());

		}
		
		medicineCatalog.save(med);
		
		inventory.save(new UniqueInventoryItem(med, Quantity.of(quantity)));

		model.addAttribute("inventory", inventory.findAll().toList());

		model.addAttribute("formular", new MedicineForm());

		return "redirect:/inventory";
	}


	//Increasing a Medicine Quantity, will mean that the Medicine is added to a waitlist that will be processed by 
	//autorestock.
	//after that the waitlist is cleared and the Medicine will be orderd by its original Quantity
	@GetMapping("/inrease")
	@PreAuthorize("hasRole('BOSS')")
	String preinreaseQuantity(Model model){

		return "redirect:/inventory";

	}

	@PostMapping("/increase")
	@PreAuthorize("hasRole('BOSS')")
	String inreaseQuantity(@ModelAttribute MedicineForm formular, Model model) {

		int val=1;

		if(waitlist.containsKey(formular.getId())){

			val+=waitlist.get(formular.getId());

		}

		waitlist.put(formular.getId(), val);

		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());
		model.addAttribute("waitlist", this.waitlist);

		return "redirect:/inventory";

	}
	
	@GetMapping("/delete")
	@PreAuthorize("hasRole('BOSS')")
	String predelete(Model model) {
		return "redirect:/inventory";
	}
	@PostMapping("/delete")
	@PreAuthorize("hasRole('BOSS')")
	String delete(@ModelAttribute MedicineForm formular, Model model) {
		
		UniqueInventoryItem item = this.findById(formular.getId());

		this.inventory.delete(item);
		this.medicineCatalog.delete((Medicine)item.getProduct());

		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());

		return "redirect:/inventory";
	
	}

	@GetMapping("/meddetail")
	@PreAuthorize("hasRole('BOSS')")
	String predetails(Model model) {
		
		return "meddetail";

	}

	@PostMapping("/meddetail")
	@PreAuthorize("hasRole('BOSS')")
	String details(@ModelAttribute MedicineForm formular, Model model) {

		MedicineForm medForm = new MedicineForm();

		if(!formular.getId().equals("")){

			UniqueInventoryItem item = this.findById(formular.getId());

			Medicine med = (Medicine)item.getProduct();

			medForm.setId(formular.getId());
			medForm.setAmount(med.getAmount());
			medForm.setDescription(med.getDescription());
			medForm.setImage(med.getImage());
			medForm.setName(med.getName());
			medForm.setPresonly(med.isPresonly());
			medForm.setPrice(med.getPrice().getNumber().doubleValue());
			medForm.setPurchasingprice(med.getPurchaseprice().getNumber().doubleValue());
			medForm.setQuantity(med.getQuantity());
			medForm.setTags(String.join(",", med.getCategories().toList()));

		}

		model.addAttribute("formular",medForm);
		model.addAttribute("inventory", inventory.findAll().toList());
		
		return "meddetail";

	}

	@GetMapping("/discard")
	@PreAuthorize("hasRole('BOSS')")
	String flush(Model model) {

		model.addAttribute("formular", new MedicineForm());

		return "redirect:/inventory";

	}
	
}

