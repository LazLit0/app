package de.freerider.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
	// launch Controllers from package: de.freerider.restapi
	basePackages = { "de.freerider.restapi" }
)
public class AppApplication {

	public static void main(String[] args) {
		System.out.println("Hello, Mave");
		SpringApplication.run(AppApplication.class, args);
	}

}
