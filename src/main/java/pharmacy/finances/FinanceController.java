package pharmacy.finances;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Optional;

import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.accountancy.AccountancyEntryIdentifier;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Ein Controller für die Finanzeinsicht.
 * Nutzt {@link AccountancyAdapter}.
 * @author Lukas Luger
 */
@Controller
@org.springframework.core.annotation.Order(20)
@EnableScheduling
public class FinanceController {
	
	private final AccountancyAdapter accountancyAdapter;
	/**
	 * Initialisiert den FinanceController.
	 * @param accountancyAdapter
	 */
	FinanceController(AccountancyAdapter accountancyAdapter) {
		
		Assert.notNull(accountancyAdapter, "AccountancyAdapter must not be null!");
		
		this.accountancyAdapter = accountancyAdapter;
		
	}
	/**
	 * Fügt notwendige Attribute zum Model hinzu, wenn die Finanzübersicht aufgerufen wird.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/finances")
	@PreAuthorize("hasRole('BOSS')")
	public String finances(Model model) {

		model.addAttribute("filterForm",new FilterForm());
		model.addAttribute("tab", this.accountancyAdapter.filter(new FilterForm()));
		model.addAttribute("plus",this.accountancyAdapter.getRevenue().getNumber().doubleValue());
		model.addAttribute("minus",this.accountancyAdapter.getExpenses().getNumber().doubleValue());
		model.addAttribute("fixcosts",this.accountancyAdapter.getFix());
		model.addAttribute("total", this.accountancyAdapter.getBalance().getNumber().doubleValue());
		model.addAttribute("fail", false);

		return "finances";

	}
	/**
	 * Wird aufgerufen wenn man auf Filtern clickt.
	 * Nimmt die {@link FilterForm} und das Model entgegen.
	 * Verucht den Filter anzuwenden und Fügt eine entsprechende HashMap zum Model hinzu.
	 * Falls dies Fehlschägt wird die Fehlervariable auf true gesetzt, sonst false.
	 * @param filterForm
	 * @param model
	 * @return der Name der Ansicht
	 */
	@PostMapping("/finances")
	@PreAuthorize("hasRole('BOSS')")
	public String financesupdate(@ModelAttribute FilterForm filterForm, Model model) {

		try{

			model.addAttribute("tab", this.accountancyAdapter.filter(filterForm));
			model.addAttribute("fail", false);

		}catch(DateTimeParseException e){

			model.addAttribute("tab", this.accountancyAdapter.filter(new FilterForm()));
			model.addAttribute("fail", true);

		}

		model.addAttribute("filterForm", filterForm);
		model.addAttribute("plus",this.accountancyAdapter.getRevenue().getNumber().doubleValue());
		model.addAttribute("minus",this.accountancyAdapter.getExpenses().getNumber().doubleValue());
		model.addAttribute("fixcosts",this.accountancyAdapter.getFix());
		model.addAttribute("total", this.accountancyAdapter.getBalance().getNumber().doubleValue());
		
		return "finances";

	}
	/**
	 * Dient zur Einsicht der eigenen Finanzen. Fügt eine HashMap mit Einträgen zum Model hinzu.
	 * (Gefiltert nach dem eingeloggtem User, falls dieser existiert, sonst: Leere HashMap)
	 * @param model
	 * @param userAccount
	 * @return der Name der Ansicht
	 */
	@GetMapping("/myfinances")
	public String myfinances(Model model, @LoggedIn Optional<UserAccount> userAccount) {

		if(userAccount.isPresent()){
	
			model.addAttribute("tab", this.accountancyAdapter.findByUserAccount(userAccount.get()));
	
		}else{
		
			model.addAttribute("tab", new HashMap<AccountancyEntry, LocalDate>());

		}
		
			return "myfinances";

	}
	/**
	 * Dient zur einsicht des Lohnzettels eines bestimmten {@link AccountancyEntry} mithilfe eines 
	 * {@link AccountancyEntryIdentifier}. Fügt ein Pair (siehe {@link AccountancyAdapter#get()}) zum
	 * Model hinzu.
	 * @param id
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/myfinances/{id}")
	public String salarypaper(@PathVariable AccountancyEntryIdentifier id,Model model) {

		model.addAttribute("det", this.accountancyAdapter.get(id));

		return "salarypaper";

	}
	/**
	 * Dient zur einsicht einer Rechnung eines bestimmten {@link AccountancyEntry} mithilfe eines 
	 * {@link AccountancyEntryIdentifier}. Fügt eine {@link Order} zum Model hinzu.
	 * @param id
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/finances/{id}")
	public String financedetail(@PathVariable AccountancyEntryIdentifier id,Model model) {

		model.addAttribute("det", this.accountancyAdapter.getOrder(id));
		
		return "financedetails";
	}
	/**
	 * Fügt die Fixkosten von {@link AccountancyAdapter} zum Model hinzu, um die Editierung der Fixkoszen zu 
	 * ermöglichen.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/finances/editfix")
	public String fix(Model model) {

		model.addAttribute("fixcosts",this.accountancyAdapter.getFix());

		return "editfix";
	}
	/**
	 * Wird beim speichern der Fixkosten in View aufgerufen.
	 * Setzt die übergebenen Fixkosten im {@link AccountancyAdapter}.
	 * Fügt diese wieder zum View hinzu und leitet zur Finanzübersicht zurück.
	 * @param fixcosts
	 * @param model
	 * @return der Name der Ansicht
	 */
	@PostMapping("/finances/editfix")
	public String fixsave(@ModelAttribute Fixcosts fixcosts,Model model) {

		this.accountancyAdapter.setFix(fixcosts);

		model.addAttribute("fixcosts",fixcosts);

		return "redirect:/finances";

	}
	/**
	 * Wenn "/create" aufgerufen wird, dann leite zur Finanzübersicht weiter.
	 * Die Seite "create" existiert nicht. Der Link dient nur zum erstellen der Beispieleinträge.
	 * @param model
	 * @return der Name der Ansicht
	 */
	@GetMapping("/create")
	public String hallo(Model model) {

		return "redirect:/finances";

	}

	/**
	 * Erstellt Beispieleinträge mithilfe des {@link AccountancyAdapter}s.
	 * (Model wird nicht verändert)
	 * @param model
	 * @return der Name der Ansicht
	 */
	@PostMapping("/create")
	public String createdefaultenties(Model model) {

		this.accountancyAdapter.createExamples();
		
		return "redirect:/finances";

	}
	
}
