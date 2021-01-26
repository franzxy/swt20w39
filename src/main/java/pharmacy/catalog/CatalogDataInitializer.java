  
package pharmacy.catalog;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

@Component
@Order(10)
class CatalogDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final MedicineCatalog medicineCatalog;

	CatalogDataInitializer(MedicineCatalog medicineCatalog) {
		Assert.notNull(medicineCatalog, "MedicineCatalog must not be null!");
		this.medicineCatalog = medicineCatalog;
	}

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

		medicineCatalog.save(new Medicine("Kopfschmerztablette 1", "Richtig gutes Zeug",
				Money.of(23, "EUR"), Money.of(10, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"),
				0.03,  false, "1",2));
		medicineCatalog.save(new Medicine("Hustensaft 1", "Saftiger Saft",
				Money.of(18, "EUR"), Money.of(15, "EUR"), Arrays.asList("natürlich", "erkältung", "hustensaft"),
				0.05, true, "2",4));
		medicineCatalog.save(new Medicine("Schmerztablette", "Schmerzi, die Schmerztablette",
				Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("tablette", "knieschmerzen", "gelenkschmerzen"),
				0.01,  false, "3",6));
		medicineCatalog.save(new Medicine("Durchfallmittel", "Richtig durch, das zeug",
				Money.of(8, "EUR"), Money.of(2, "EUR"), Arrays.asList("tablette", "durchfall", "erbrechen"),
				0.05, false, "4",5));
		medicineCatalog.save(new Medicine("Heilsable", "Heilt alles weg",
				Money.of(10, "EUR"), Money.of(8, "EUR"), Arrays.asList("salbe", "wunde", "verletzung"),
				0.08, false, "5",7));
		medicineCatalog.save(new Medicine("Placebo Tee", "Nur kalt zubereiten",
				Money.of(20, "EUR"), Money.of(2, "EUR"), Arrays.asList("tee", "betäubend", "entspannung"),
				0.1, true, "6",8));
		medicineCatalog.save(new Medicine("Hustenbonbons", "Zum lutschen und schlucken",
				Money.of(3, "EUR"), Money.of(0.5, "EUR"),Arrays.asList("halsschmerzen", "husten", "fiber"),
				0.03, false, "7",9));
		medicineCatalog.save(new Medicine("Heilkonzentrat", "Ein Tropfen drauf, schon geheilt",
				Money.of(19, "EUR"), Money.of(5, "EUR"), Arrays.asList("fußpilz", "konzentrat", "natürlich"),
				0.07, true, "8",10));
		medicineCatalog.save(new Medicine("Schmerzsalbe", "Wenn es wieder richtig am schmerzen ist",
				Money.of(30, "EUR"), Money.of(23, "EUR"), Arrays.asList("salbe", "rücken", "gelenke"),
				0.2,  false, "9",3));
		medicineCatalog.save(new Medicine("Hautcreme", "Für häutende Haut",
				Money.of(14, "EUR"), Money.of(10, "EUR"), Arrays.asList("ausschlag", "creme", "betäubend"),
				0.03,  true, "10",4));
		medicineCatalog.save(new Medicine("Allergiemittel","Für Allergiker mit hohem Niveau",
				Money.of(9, "EUR"), Money.of(4, "EUR"), Arrays.asList("allergie", "atemnot", "spritze"),
				0.03,  true, "11",5));
		medicineCatalog.save(new Medicine("Insulin", "Gut insuliertes Insolium Insulites",
				Money.of(70, "EUR"), Money.of(50, "EUR"), Arrays.asList("spritze", "schwindel", "unterzucker"),
				0.5,  true, "15",5));
		medicineCatalog.save(new Medicine("Augentropfen", "Für kleine und große Augen",
				Money.of(10, "EUR"), Money.of(3, "EUR"), Arrays.asList("augentropfen", "sehschwäche", "orientierungslosigkeit"),
				0.05, false, "13",7));
		medicineCatalog.save(new Medicine("Nasenspray", "Für eine gereitzte Nase",
				Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("verstopft", "nasenspray", "nase"),
				0.015,  false, "14",7));
		medicineCatalog.save(new Medicine("Hustenbonbon (einzeln)", "Klein aber fein",
				Money.of(0.10, "EUR"), Money.of(0.04, "EUR"),Arrays.asList("schluckbeschwerden", "halsschmerzen", "lutschen"),
				0.001,  false, "12",6));
		medicineCatalog.save(new Medicine("Kopfschmerztablette 2", "Wenn der Kater nicht aufhören will", Money.of(12, "EUR"),
				Money.of(7, "EUR"),  Arrays.asList("tablette", "kopfschmerzen", "fiber"),
				0.05, true, "32",8));
		medicineCatalog.save(new Medicine("Kopfschmerztablette 3", "Richtig richtig gutes Zeug",
				Money.of(23, "EUR"), Money.of(10, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"),
				0.03,  true, "16",9));
		medicineCatalog.save(new Medicine("Hustensaft 2", "Saftiger saft, frisch gepresst",
				Money.of(18, "EUR"), Money.of(15, "EUR"), Arrays.asList("natürlich", "erkältung", "hustensaft"),
				0.05,false, "17",10));
		medicineCatalog.save(new Medicine("Schmerztablette 3000", "Die Schmerztablette, für richtig große Schmerzen",
				Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("tablette", "knieschmerzen", "gelenkschmerzen"),
				0.01,  true, "18",11));
		medicineCatalog.save(new Medicine("Durchfallmittel XZ900", "Professionelle Durhfallhilfe",
				Money.of(8, "EUR"), Money.of(2, "EUR"), Arrays.asList("tablette", "durchfall", "erbrechen"),
				0.05, true, "19",13));
		medicineCatalog.save(new Medicine("Heilsable HELBA", "Heilt alles, restlos, zuverlässig",
				Money.of(10, "EUR"), Money.of(8, "EUR"), Arrays.asList("salbe", "wunde", "verletzung"),
				0.08,  false, "20",15));
		medicineCatalog.save(new Medicine("Placebo Tee Plus", "Kalt und warm genießbar",
				Money.of(20, "EUR"), Money.of(2, "EUR"), Arrays.asList("tee", "betäubend", "entspannung"),
				0.1, true, "21",4));
		medicineCatalog.save(new Medicine("Hustenbonbons Eukalyptus", "Angenehm, wärmend",
				Money.of(3, "EUR"), Money.of(0.5, "EUR"), Arrays.asList("halsschmerzen", "husten", "fiber"),
				0.03,  false, "22",5));
		medicineCatalog.save(new Medicine("Heilkonzentrat Tropfen", "Einzeln verpackte Tropfen",
				Money.of(19, "EUR"), Money.of(5, "EUR"), Arrays.asList("fußpilz", "konzentrat", "natürlich"),
				0.07, true, "23",5));
		medicineCatalog.save(new Medicine("Schmerzsalbe ANGELA", "Nicht nur für Angela",
				Money.of(30, "EUR"), Money.of(23, "EUR"), Arrays.asList("salbe", "rücken", "gelenke"),
				0.2,  true, "24",7));
		medicineCatalog.save(new Medicine("Hautcreme MEME-Creme", "Cremig, weich, erfischend",
				Money.of(14, "EUR"), Money.of(10, "EUR"), Arrays.asList("ausschlag", "creme", "betäubend"),
				0.03, false, "25",7));
		medicineCatalog.save(new Medicine("Allergiemittel Allergika", "Für jede Allergie anwendbar",
				Money.of(9, "EUR"), Money.of(4, "EUR"), Arrays.asList("allergie", "atemnot", "spritze"),
				0.03,  true, "26",8));
		medicineCatalog.save(new Medicine("Insulin Lotus", "Aus frischen Lotusblättern",
				Money.of(70, "EUR"), Money.of(50, "EUR"), Arrays.asList("spritze", "schwindel", "unterzucker"),
				0.5,  true, "27",9));
		medicineCatalog.save(new Medicine("Augentropfen Kolibri", "Für trockene und gereitzte Augen",
				Money.of(10, "EUR"), Money.of(3, "EUR"), Arrays.asList("augentropfen", "sehschwäche", "orientierungslosigkeit"),
				0.05, true, "28",6));
		medicineCatalog.save(new Medicine("Nasenspray Salzwasser", "Für einen frischen Durchzug",
				Money.of(5, "EUR"), Money.of(3, "EUR"), Arrays.asList("verstopft", "nasenspray", "nase"),
				0.015, true, "29",5));
		medicineCatalog.save(new Medicine("Hustenbonbon Traube (einzeln)", "Aus handverlesenen Trauben",
				Money.of(0.10, "EUR"), Money.of(0.04, "EUR"),Arrays.asList("schluckbeschwerden", "halsschmerzen", "lutschen"),
				0.001, false, "30",4));
		medicineCatalog.save(new Medicine("Kopfschmerztablette 4", "Die Härteste, für ganz harten Schmerz",
				Money.of(12, "EUR"), Money.of(7, "EUR"), Arrays.asList("tablette", "kopfschmerzen", "fiber"),
				0.05, true, "31",3));
	}
}