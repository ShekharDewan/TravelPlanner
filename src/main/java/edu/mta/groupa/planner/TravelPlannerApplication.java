package edu.mta.groupa.planner;

import java.util.Date;

//import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Reservation;
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
		
		final Lorem lorem = LoremIpsum.getInstance();
		
        final Trip trip = new Trip();
        trip.setTitle(RandomStringUtils.randomAlphabetic(10));
        trip.setDescription(lorem.getWords(25));
        trip.setStart(new Date());
                
        trip.getDestinations().add("New York City");
        trip.getDestinations().add("Moncton");
        
        // add itineraries
        trip.getItineraries().add(new Itinerary(trip, new Date(), lorem.getParagraphs(2, 4)));
        trip.getItineraries().add(new Itinerary(trip, new Date(), lorem.getParagraphs(2, 4)));
        trip.getItineraries().add(new Itinerary(trip, new Date(), lorem.getParagraphs(2, 4)));
        
        // random address
        Address address = new Address(42, "Gallifrey Street", "New Tokyo", "Japan", "90210", 42.373399,-71.115985);
        
        // add accomodations
        trip.getAccomodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));
        trip.getAccomodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));
        trip.getAccomodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));
        trip.getAccomodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));

        // add reservations
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        
        return trip;
    }
}
