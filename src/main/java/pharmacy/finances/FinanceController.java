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

@Controller
@org.springframework.core.annotation.Order(20)
@EnableScheduling
public class FinanceController {
	
	private final AccountancyAdapter accountancyAdapter;
	
	FinanceController(AccountancyAdapter accountancyAdapter) {
		
		Assert.notNull(accountancyAdapter, "AccountancyAdapter must not be null!");
		
		this.accountancyAdapter = accountancyAdapter;
		
	}
	
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

	@GetMapping("/myfinances")
	public String myfinances(Model model, @LoggedIn Optional<UserAccount> userAccount) {

		if(userAccount.isPresent()){
	
			model.addAttribute("tab", this.accountancyAdapter.findByUserAccount(userAccount.get()));
	
		}else{
		
			model.addAttribute("tab", new HashMap<AccountancyEntry, LocalDate>());

		}
		
			return "myfinances";

	}

	@GetMapping("/myfinances/{id}")
	public String salarypaper(@PathVariable AccountancyEntryIdentifier id,Model model) {

		model.addAttribute("det", this.accountancyAdapter.get(id));

		return "salarypaper";

	}

	@GetMapping("/finances/{id}")
	public String financedetail(@PathVariable AccountancyEntryIdentifier id,Model model) {

		model.addAttribute("det", this.accountancyAdapter.getOrder(id));
		
		return "financedetails";
	}
	
	@GetMapping("/finances/editfix")
	public String fix(Model model) {

		model.addAttribute("fixcosts",this.accountancyAdapter.getFix());

		return "editfix";
	}

	@PostMapping("/finances/editfix")
	public String fixsave(@ModelAttribute Fixcosts fixcosts,Model model) {

		this.accountancyAdapter.setFix(fixcosts);

		model.addAttribute("fixcosts",fixcosts);

		return "redirect:/finances";

	}
	
	@GetMapping("/create")
	public String hallo(Model model) {

		return "redirect:/finances";

	}

	@PostMapping("/create")
	public String createdefaultenties(Model model) {

		this.accountancyAdapter.createExamples();
		
		return "redirect:/finances";

	}
	
}
