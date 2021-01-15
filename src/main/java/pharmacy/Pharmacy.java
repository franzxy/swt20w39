package pharmacy;

import java.util.HashMap;
import java.util.Map;

import org.salespointframework.EnableSalespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableSalespoint
public class Pharmacy {
	
	public static void main(String[] args) {
		SpringApplication.run(Pharmacy.class, args);
	}

	@Configuration
	static class PharmacyWebConfiguration implements WebMvcConfigurer {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
			registry.addViewController("/").setViewName("index");
		}
	}

	@Configuration
	static class WebSecurityConfiguration extends SalespointSecurityConfiguration {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();

			http.authorizeRequests().antMatchers("/**").permitAll().and().
				formLogin().loginPage("/login").loginProcessingUrl("/login").and().
				logout().logoutUrl("/logout").logoutSuccessUrl("/");
		}
	}
	@Configuration
	public class WaitingListConfig {
		@Bean 
   		public Map<String, Integer> waitlist(){
      		Map<String, Integer> map = new HashMap<String, Integer>();
      	return map;      
   }
	
	}
}
