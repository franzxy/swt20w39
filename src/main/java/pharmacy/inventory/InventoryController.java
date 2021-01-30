package pharmacy.inventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
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
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;

/**
 * Ein Controller für:
 * <ul>
 * 	<li>Warenübersicht</li>
 * <li>Hinzufügen, Bearbeiten und Löschen von Medikamenten</li>
 * <li>Automatisches nachbestellen</li>
 * </ul>
 * @author Lukas Luger
 */
@Controller
@EnableScheduling
class InventoryController {

	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;

	@Autowired
	private MedicineCatalog medicineCatalog;

	@Autowired
	private final Accountancy accountancy;

	@Autowired
	private BusinessTime time;
	
	private LocalDateTime now;

	@Autowired
	private Map<String, Integer> waitlist;
	

	/**
	 * Initialisiert den Controller.
	 * @param inventory
	 * @param medicineCatalog
	 * @param userAccount
	 * @param orderManagement
	 * @param time
	 * @param waitlist
	 */
	InventoryController(UniqueInventory<UniqueInventoryItem> inventory, MedicineCatalog medicineCatalog, 
			Accountancy accountancy, BusinessTime time, Map<String, Integer> waitlist) {

		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");
		Assert.notNull(accountancy, "OrderManagement must not be null!");
		Assert.notNull(time, "BusinessTime must not be null!");
		Assert.notNull(waitlist, "Waitlist must not be null!");

		this.inventory = inventory;
		this.medicineCatalog=medicineCatalog;
		this.accountancy=accountancy;
		this.waitlist=waitlist;
		this.time=time;
		this.now=LocalDateTime.now();
		
	}
	/**
	 * Geht alle Medikamente im Inventar durch und vergleicht ist mit soll Wert des Vorhandenseins.
	 * Zusätzlich wird dies mit der "waitlist" abgeglichen und letzendlich {@link #restock(int, String)} aufgreufen.
	 * Zusätzlich wird die
	 * Die "waitlist" wird geleert.
	 */
	private void autorestock(){

		this.inventory.findAll().forEach(item->{

			int anz = 0;

			String id = item.getId().getIdentifier();

			Medicine med = (Medicine)item.getProduct();

			if(!item.hasSufficientQuantity(Quantity.of(med.getQuantity()))){

				anz = med.getQuantity() - item.getQuantity().getAmount().intValue();
			
			}

			if(this.waitlist.containsKey(id)){

				anz += this.waitlist.get(id);

			}

			if(anz!=0){

				this.restock(anz, id);

			}	

		});
		this.waitlist = new HashMap<String, Integer>();

	}
	/**
	 * Findet ein Item aus dem Inventar mithilfe eines Strings als id.
	 * @param id
	 * @return {@link UniqueInventoryItem}
	 */
	private UniqueInventoryItem findById(String id){

		return this.inventory.findAll().filter(item ->{

			return item.getId().getIdentifier().equals(id);

		}).stream().findFirst().get();

	}
	/**
	 * Falls ende des Tages: rufe {@link #autorestock()} auf.
	 */
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
	/**
	 * Erstellt einen {@link AccountancyEntry} für die x malige Nachbestellung des übergebenen Medikaments.
	 * Gleichzeitig wird das Medikament aufgestockt.
	 * @param anz
	 * @param id
	 */
	private void restock(int anz, String id){

		UniqueInventoryItem item = this.findById(id);

		Medicine med = (Medicine) item.getProduct();

		inventory.save(item.increaseQuantity(Quantity.of(anz)));
		
		
		AccountancyEntry order = new AccountancyEntry(med.getPurchaseprice().multiply((long)(-1*anz)),
			"Nachbestellung von "+anz+" mal "+med.getName());

		this.accountancy.add(order);
	
	}

	/**
	 * Fügt notwendige Attribute zum Model hinzu, wenn die Inventarübersicht aufgerufen wird.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String inventory(Model model) {

		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());
		model.addAttribute("waitlist", this.waitlist);

		return "inventory";
	
	}

	/**
	 * Siehe {@link #inventory}
	 * (Existiert falls jemals "/inventory" mit "post" aufgerufen wird)
	 * @param model
	 * @return der Name der Ansicht
	 */
	@PostMapping("/inventory")
	@PreAuthorize("hasRole('BOSS')")
	String filtern( Model model) {

		model.addAttribute("inventory", inventory.findAll().toList());
		model.addAttribute("formular", new MedicineForm());
		model.addAttribute("waitlist", this.waitlist);

		return "inventory";

	}
	/**
	 * Falls "/addmed" aufgerufen wird, wird direkt zu "/inventory" ungeleitet, da addmed nicht existiert.
	 * 
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/addmed")
	@PreAuthorize("hasRole('BOSS')")
	String premed(Model model) {
		
		return "redirect:/inventory";

	}
	/**
	 * Wenn "/addmed" mit "post" aufgerufen wird, wird die übergebene {@link MedicineForm} ausgewertet.
	 * Das heißt, es wird geprüft ob ein Medikament erstellt oder nur verändert wurde mittels der id des Formulars.
	 * Falls das Medikament existiert wird dieses Gelöscht und als neues Medikament hinzugefügt.
	 * Falls es ein neues ist wird es nur Hinzugefügt. Das Formular wird zusätzlich auf fehler überprüft.
	 * (Das Hinzufügen und entfernen erfolgt sowohl im Inventar als auch im Katalog)
	 * @param formular
	 * @param result
	 * @param model
	 * @return der Name der Ansicht
	 */
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

	/**
	 * Falls "/increase" aufgerufen wird, wird direkt zu "/inventory" ungeleitet, da increase nicht existiert.
	 * @param model
	 * @return der Name der Ansicht
	 */
	//Increasing a Medicine Quantity, will mean that the Medicine is added to a waitlist that will be processed by 
	//autorestock.
	//after that the waitlist is cleared and the Medicine will be orderd by its original Quantity
	@GetMapping("/inrease")
	@PreAuthorize("hasRole('BOSS')")
	String preinreaseQuantity(Model model){

		return "redirect:/inventory";

	}
	/**
	 * Diese Methode wird bei dem clicken des "+1" Buttons im Inventar ausgelößt.
	 * Als Identifikator wird die {@link MedicineForm} genutzt mittels id.
	 * Fügt das Medikament in Form einer String id mit +1 zur "waitlist" hinzu.
	 * Leitet zu "/inventory" um.
	 * @param formular
	 * @param model
	 * @return der Name der Ansicht
	 */
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
	/**
	 * Falls "/delete" aufgerufen wird, wird direkt zu "/inventory" ungeleitet, da delete nicht existiert.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/delete")
	@PreAuthorize("hasRole('BOSS')")
	String predelete(Model model) {
		return "redirect:/inventory";
	}

	/**
	 * Diese Methode wird bei dem clicken des Löschen Buttons im Inventar ausgelößt.
	 * Als Identifikator wird die {@link MedicineForm} genutzt mittels id.
	 * Löscht das Medikament aus dem Inventar und dem Katalog.
	 * Fügt das neue Inventar zum Model hinzu.
	 * Leitet zu "/inventory" um.
	 * @param formular
	 * @param model
	 * @return der Name der Ansicht
	 */
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
	/**
	 * Falls "/meddetail" aufgerufen wird, wird zu "/meddetail" weitergeleitet.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/meddetail")
	@PreAuthorize("hasRole('BOSS')")
	String predetails(Model model) {
		
		return "meddetail";

	}
	/**
	 * Ist für die Editierungsseite eine Medikaments zuständig.
	 * Falls die Id im übergebenen Formular nicht leer ist wird das Formular mit den passenden Attributen
	 * der dazugehörigen Medizin gefüllt und zum Model hinzugefügt.
	 * Falls die Id jedoch leer ist, wird ein Leeres Formular zum Model hinzugefügt.
	 * @param formular
	 * @param model
	 * @return der Name der Ansicht
	 */
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
	/**
	 * Falls Abgebrochen wird, wird das Formular zur Sicherheit hier geleert.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/discard")
	@PreAuthorize("hasRole('BOSS')")
	String flush(Model model) {

		model.addAttribute("formular", new MedicineForm());

		return "redirect:/inventory";

	}
	
}

