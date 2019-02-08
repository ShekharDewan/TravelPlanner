package edu.mta.groupa.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This is the entry point for the application. It boots up a Spring container
 * which instances all of the necessary model Entities, mapping them to an H2
 * embedded database, as defined in application.properties
 * 
 * @author Maryse
 *
 */
@EnableJpaRepositories("edu.mta.groupa.planner.repository")
@EntityScan("edu.mta.groupa.planner.model")
@SpringBootApplication
public class TravelPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelPlannerApplication.class, args);
	}
}
