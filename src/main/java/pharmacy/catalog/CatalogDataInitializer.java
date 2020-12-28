  
package pharmacy.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.quantity.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * A {@link DataInitializer} implementation that will create dummy data for the
 * application on application startup.
 *
 * @author Paul Henke
 * @author Oliver Gierke
 * @see DataInitializer
 */
@Component
@Order(20)
class CatalogDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final MedicineCatalog medicineCatalog;

	CatalogDataInitializer(MedicineCatalog medicineCatalog) {

		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");

		this.medicineCatalog = medicineCatalog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.salespointframework.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		if (medicineCatalog.findAll().iterator().hasNext()) {
			return;
		}

		LOG.info("Creating default catalog entries.");
		/**ArrayList<List<String>> cats = new ArrayList<List<String>>();
		cats.add(Arrays.asList("tablette", "kopfschmerzen", "fiber"));	//Kopfschmerztablette
		cats.add(Arrays.asList("natürlich", "erkältung", "hustensaft")); //Hustensaft
		cats.add(Arrays.asList("tablette", "knieschmerzen", "gelenkschmerzen"));	//Schmerztablette
		cats.add(Arrays.asList("tablette", "durchfall", "erbrechen")); //Durchfallmittel
		cats.add(Arrays.asList("salbe", "wunde", "verletzung")); //Heilsalbe
		cats.add(Arrays.asList("tee", "betäubend", "entspannung"));	//Placebo Tee
		cats.add(Arrays.asList("halsschmerzen", "husten", "fiber")); //Hustenbonbon
		cats.add(Arrays.asList("fußpilz", "konzentrat", "natürlich"));	//Heilkonzentrat 2
		cats.add(Arrays.asList("salbe", "rücken", "gelenke")); //Schmerzsalbe
		cats.add(Arrays.asList("ausschlag", "creme", "betäubend")); //Hautcreme
		cats.add(Arrays.asList("allergie", "atemnot", "spritze")); //Allergiemittel
		cats.add(Arrays.asList("spritze", "schwindel", "unterzucker")); //insulin
		cats.add(Arrays.asList("augentropfen", "sehschwäche", "orientierungslosigkeit")); //Augentropfen
		cats.add(Arrays.asList("verstopft", "nasenspray", "nase")); //Nasenspray
		cats.add(Arrays.asList("schluckbeschwerden", "halsschmerzen", "lutschen")); //bonbon**/
		


		medicineCatalog.save(new Medicine("Kopfschmerztablette 1",				"richtig gutes zeug", Money.of(23, "EUR"), Money.of(10, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"), 0.03, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Hustensaft 1",								"saftiger saft",			Money.of(18, "EUR"), Money.of(15, "EUR"), Arrays.asList("natürlich", "erkältung", "hustensaft"), 0.05, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Schmerztablette", "Schmerzi, die Schmerztablette",	Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("tablette", "knieschmerzen", "gelenkschmerzen"), 0.01, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Durchfallmittel", "richtig durch, das zeug", 				Money.of(8, "EUR"), Money.of(2, "EUR"), Arrays.asList("tablette", "durchfall", "erbrechen"), 0.05, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Heilsable", "Heilt alles weg",											Money.of(10, "EUR"), Money.of(8, "EUR"), Arrays.asList("salbe", "wunde", "verletzung"), 0.08, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Placebo Tee", "nur kalt zubereiten", 								Money.of(20, "EUR"), Money.of(2, "EUR"), Arrays.asList("tee", "betäubend", "entspannung"), 0.1, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Hustenbonbons", "zum lutschen und schlucken", 		Money.of(3, "EUR"), Money.of(0.5, "EUR"),Arrays.asList("halsschmerzen", "husten", "fiber"), 0.03, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Heilkonzentrat", "ein tropfen drauf, schon geheilt", 	Money.of(19, "EUR"), Money.of(5, "EUR"), Arrays.asList("fußpilz", "konzentrat", "natürlich"), 0.07, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Schmerzsalbe", "wenn es wieder richtig am schmerzen ist", Money.of(30, "EUR"), Money.of(23, "EUR"), Arrays.asList("salbe", "rücken", "gelenke"), 0.2, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Hautcreme", "für häutende Haut", 											Money.of(14, "EUR"), Money.of(10, "EUR"), Arrays.asList("ausschlag", "creme", "betäubend"), 0.03, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Allergiemittel", "für allergiker mit hohem niveau",				   Money.of(9, "EUR"), Money.of(4, "EUR"), Arrays.asList("allergie", "atemnot", "spritze"), 0.03, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Insulin", "gut insuliertes Insolium Insulites", 							 Money.of(70, "EUR"), Money.of(50, "EUR"), Arrays.asList("spritze", "schwindel", "unterzucker"), 0.5, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Augentropfen", "für kleine und große Augen",						   Money.of(10, "EUR"), Money.of(3, "EUR"), Arrays.asList("augentropfen", "sehschwäche", "orientierungslosigkeit"), 0.05, Metric.LITER, false, ""));
		medicineCatalog.save(new Medicine("Nasenspray", "für eine gereitzte Nase", 									  Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("verstopft", "nasenspray", "nase"), 0.015, Metric.LITER, false, ""));
		medicineCatalog.save(new Medicine("Hustenbonbon (einzeln)", "klein aber fein",                             Money.of(0.10, "EUR"), Money.of(0.04, "EUR"),Arrays.asList("schluckbeschwerden", "halsschmerzen", "lutschen"), 0.001, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Kopfschmerztablette 2", "wenn der Kater nicht aufhören will", Money.of(12, "EUR"), Money.of(7, "EUR"),  Arrays.asList("tablette", "kopfschmerzen", "fiber"), 0.05, Metric.KILOGRAM, true, ""));	

		medicineCatalog.save(new Medicine("Kopfschmerztablette 3", "richtig richtig gutes zeug",									Money.of(23, "EUR"), Money.of(10, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"), 0.03, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Hustensaft 2", "saftiger saft, frisch gepresst",													Money.of(18, "EUR"), Money.of(15, "EUR"), Arrays.asList("natürlich", "erkältung", "hustensaft"), 0.05, Metric.LITER, false, ""));
		medicineCatalog.save(new Medicine("Schmerztablette 3000", "die Schmerztablette, für richtig große Schmerzen", Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("tablette", "knieschmerzen", "gelenkschmerzen"), 0.01, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Durchfallmittel XZ900", "Professionelle Durhfallhilfe",                                         Money.of(8, "EUR"), Money.of(2, "EUR"), Arrays.asList("tablette", "durchfall", "erbrechen"), 0.05, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Heilsable HELBA", "Heilt alles, restlos, zuverlässig",                                              Money.of(10, "EUR"), Money.of(8, "EUR"), Arrays.asList("salbe", "wunde", "verletzung"), 0.08, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Placebo Tee Plus", "Kalt und warm genießbar",                                                     Money.of(20, "EUR"), Money.of(2, "EUR"), Arrays.asList("tee", "betäubend", "entspannung"), 0.1, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Hustenbonbons Eukalyptus", "angenehm, wärmend",                                         Money.of(3, "EUR"), Money.of(0.5, "EUR"), Arrays.asList("halsschmerzen", "husten", "fiber"), 0.03, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Heilkonzentrat Tropfen", "einzel verpackte Tropfen",                                            Money.of(19, "EUR"), Money.of(5, "EUR"), Arrays.asList("fußpilz", "konzentrat", "natürlich"), 0.07, Metric.LITER,true, ""));
		medicineCatalog.save(new Medicine("Schmerzsalbe ANGELA", "nicht nur für Angela",                                                     Money.of(30, "EUR"), Money.of(23, "EUR"), Arrays.asList("salbe", "rücken", "gelenke"), 0.2, Metric.KILOGRAM, true, ""));
		medicineCatalog.save(new Medicine("Hautcreme MEME-Creme", "Cremig, weich, erfischend",                                     Money.of(14, "EUR"), Money.of(10, "EUR"), Arrays.asList("ausschlag", "creme", "betäubend"), 0.03, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Allergiemittel Allergika", "für jede Allergie anwendbar",                                      Money.of(9, "EUR"), Money.of(4, "EUR"), Arrays.asList("allergie", "atemnot", "spritze"), 0.03, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Insulin Lotus", "aus frischen Lotusblättern",                                                           Money.of(70, "EUR"), Money.of(50, "EUR"), Arrays.asList("spritze", "schwindel", "unterzucker"), 0.5, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Augentropfen Kolibri", "für trockene und gereitzte Augen",                                Money.of(10, "EUR"), Money.of(3, "EUR"), Arrays.asList("augentropfen", "sehschwäche", "orientierungslosigkeit"), 0.05, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Nasenspray Salzwasser", "Für einen frischen Durchzug",                                    Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("verstopft", "nasenspray", "nase"), 0.015, Metric.LITER, true, ""));
		medicineCatalog.save(new Medicine("Hustenbonbon Traube (einzeln)", "aus handverlesenen Trauben",                    Money.of(0.10, "EUR"), Money.of(0.04, "EUR"),Arrays.asList("schluckbeschwerden", "halsschmerzen", "lutschen"), 0.001, Metric.KILOGRAM, false, ""));
		medicineCatalog.save(new Medicine("Kopfschmerztablette 4", "Die härteste, für ganz harten schmerz",                    Money.of(12, "EUR"), Money.of(7, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"), 0.05, Metric.KILOGRAM, true, ""));		
	}
}