package edu.mta.groupa.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import edu.mta.groupa.planner.repository.TripRepository;

@EnableJpaRepositories("edu.mta.groupa.planner.repository") 
@EntityScan("edu.mta.groupa.planner.model")
@SpringBootApplication
public class TravelPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelPlannerApplication.class, args);
		setup();
	}

	static void setup() {

	}
}
