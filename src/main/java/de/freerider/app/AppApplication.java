package de.freerider.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import de.freerider.datamodel.Customer;
import de.freerider.repository.CustomerRepository;

@SpringBootApplication
@ComponentScan(
		// launch Controllers from package: de.freerider.restapi
		basePackages = { "de.freerider.restapi", "de.freerider.repository" })
public class AppApplication {

	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		System.out.println("Hello, Mave");
		SpringApplication.run(AppApplication.class, args);
	}

	@EventListener( ApplicationReadyEvent.class )
	public void runAfterSpringStartUp(){
		customerRepository.save( new Customer()
		.setId( 1 ).setName( "Eric", "Meyer" ).addContact( "eric98@yahoo.com" )
		.addContact( "(030) 7000-640000" )
		);
		customerRepository.save( new Customer()
		.setId(2).setName("Anne", "Bayer").addContact( "anne24@yahoo.de" )
		.addContact( "(030) 3481-23352" )
		);
		customerRepository.save( new Customer()
		.setId(3).setName( "Tim", "Schulz-Mueller" ).addContact( "tim2346@gmx.de" )
		);
		long count = customerRepository.count();
		System.out.println( "repository<Customer> with: " + count + "entries" );
	}

}
