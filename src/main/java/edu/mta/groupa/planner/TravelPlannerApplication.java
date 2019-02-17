package edu.mta.groupa.planner;

//import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.apache.commons.lang3.RandomStringUtils;


import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Trip;
import io.restassured.RestAssured;
import io.restassured.response.Response;

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
	
	private static final String API_ROOT = "http://localhost:8080/api/trips";

	public static void main(String[] args) {
		SpringApplication.run(TravelPlannerApplication.class, args);
		
		// create a few random trips
		createTrips(5);
	}
	
	private static void createTrips(final int numberOfTrips){
		
		for(int i = 0; i < numberOfTrips; i++){
			final Trip trip = createRandomTrip();
	
	        final Response response = RestAssured.given()
	            .contentType(MediaType.APPLICATION_JSON_VALUE)
	            .body(trip)
	            .post(API_ROOT);
	
		}
	}
	
	private static Trip createRandomTrip() {
        final Trip trip = new Trip();
        trip.setTitle(RandomStringUtils.randomAlphabetic(10));
        trip.setStart(new Date());
        
        Accommodation a = new Accommodation();
        
        trip.getDestinations().add("New York City");
        trip.getDestinations().add("Moncton");
        
        return trip;
    }
}
