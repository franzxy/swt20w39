package pharmacy;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.moduliths.docs.Documenter;
import org.moduliths.docs.Documenter.Options;
import org.moduliths.model.Module;
import org.moduliths.model.Modules;
import org.springframework.util.StringUtils;

@TestInstance(Lifecycle.PER_CLASS)
class PharmacyModularityTests {

	Modules modules = Modules.of(Pharmacy.class);
	Predicate<Module> isSalespointModule = it -> it.getBasePackage().getName().startsWith("org.salespoint");

	/*@Test
	void assertModularity() {
		modules.verify();
	}*/

	@Test
	void writeComponentDiagrams() throws IOException {

		Options options = Options.defaults()
				.withColorSelector(this::getColorForModule)
				.withDefaultDisplayName(this::getModuleDisplayName)
				.withTargetOnly(isSalespointModule);

		Documenter documenter = new Documenter(modules);
		documenter.writeModulesAsPlantUml(options);

		modules.stream().filter(isSalespointModule.negate())
				.forEach(it -> documenter.writeModuleAsPlantUml(it, options));
	}

	private Optional<String> getColorForModule(Module module) {

		String packageName = module.getBasePackage().getName();

		if (packageName.startsWith("org.salespoint")) {
			return Optional.of("#ddddff");
		} else if (packageName.startsWith("pharmacy")) {
			return Optional.of("#ddffdd");
		} else {
			return Optional.empty();
		}
	}

	private String getModuleDisplayName(Module module) {

		return module.getBasePackage().getName().startsWith("pharmacy") //
				? "pharmacy :: ".concat(StringUtils.capitalize(module.getDisplayName())) //
				: module.getDisplayName();
	}
}
