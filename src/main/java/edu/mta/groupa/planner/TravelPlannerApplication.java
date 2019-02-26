package edu.mta.groupa.planner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(TravelPlannerApplication.class, args);
		
		// create a few random trips
		createRandomTrips(2);

		createTrip();
			}
	
	private static void createRandomTrips(final int numberOfTrips) throws ParseException{
		
		for(int i = 0; i < numberOfTrips; i++){
			final Trip trip = createRandomTrip();
	
	        final Response response = RestAssured.given()
	            .contentType(MediaType.APPLICATION_JSON_VALUE)
	            .body(trip)
	            .post(API_ROOT);
	
		}
	}
	
	private static void createTrip() throws ParseException {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Trip trip = new Trip();
		trip.setTitle("Canadian Rockies");
        trip.setDescription("Canadian Rockies: one of the most beautiful places in the world");
        trip.setStart(format.parse("2019-07-01"));
        trip.setEnd(format.parse("2019-07-15"));
        
        //add itineraries
        trip.getItineraries().add(new Itinerary(trip, format.parse("2019-07-01"), "Happy to start my trip!"));
        trip.getItineraries().add(new Itinerary(trip, format.parse("2019-07-02"), "Hiking up Sulfur Mountain"));
        Address addressBanff = new Address(405, "Spray Ave", "Banff", "AB","Canada", "T1L 1J4", 51.1643,
    			-115.5618);
        Address addressJasper = new Address(1, "Old Lodge Rd", "Jasper","AB", "Canada", "T0E 1E0", 52.8861,-118.0572
    			);
     // add accommodations
        trip.getAccommodations().add(new Accommodation(trip, addressBanff, "Banff", format.parse("2019-07-01"), format.parse("2019-07-08"), "Banff Springs Hotel", 99.99));
        trip.getAccommodations().add(new Accommodation(trip, addressJasper, "Jasper", format.parse("2019-07-08"), format.parse("2019-07-15"), "Jasper Park Lodge", 99.99));
        final Response response = RestAssured.given()
	            .contentType(MediaType.APPLICATION_JSON_VALUE)
	            .body(trip)
	            .post(API_ROOT);
		
	}
	
	private static Trip createRandomTrip() throws ParseException {
		
		
		
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
        Address address = new Address(42, "Gallifrey Street", "New Tokyo","Shinuku", "Japan", "90210", 42.373399,-71.115985);
                
        // add accommodations
        trip.getAccommodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));
        trip.getAccommodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));
        trip.getAccommodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));
        trip.getAccommodations().add(new Accommodation(trip, address, lorem.getWords(1), new Date(), new Date(), lorem.getParagraphs(2, 4), 42.99));

        // add reservations
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        trip.getReservations().add(new Reservation(trip, address, lorem.getWords(1), new Date(), lorem.getWords(50), 79.99, lorem.getWords(1)));
        
        return trip;
    }
}
