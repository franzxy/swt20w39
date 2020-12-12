package pharmacy.counters;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import pharmacy.catalog.Medicine;
import pharmacy.catalog.MedicineCatalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import static org.salespointframework.core.Currencies.EURO;

@Controller
public class CounterController {

	@GetMapping("/counter")
	String counter() {
		return "counter";
	}

	@GetMapping("/selfservicecounter")
	@PreAuthorize("hasRole('BOSS')")
	public String selfServiceCounter(MedicineCatalog catalog, Model model) {

		model.addAttribute("catalog", catalog.findByPresType(Medicine.PrescriptionType.WITHOUTPRES));

		return "selfservicecounter";
	}

	@GetMapping("/doctorsstorage")
	//@PreAuthorize("hasRole('BOSS')")
	public String doctorsStorage(Model model) {
		model.addAttribute("DoctorsStorageForm", new DoctorsStorageForm());

		return "doctorsstorageform";
	}

	@PostMapping("/doctorsstorage")
	public String submitDoctorsStorageForm(@ModelAttribute DoctorsStorageForm form, Model model) {
		model.addAttribute("DoctorsStorageForm", form);
		return "redirect:/medicine/" + form.getSearchID();

	}
}
