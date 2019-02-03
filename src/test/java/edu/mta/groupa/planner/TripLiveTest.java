package edu.mta.groupa.planner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import edu.mta.groupa.planner.model.Accomodation;
import edu.mta.groupa.planner.model.Trip;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class TripLiveTest {

    private static final String API_ROOT = "http://localhost:8080/api/trips";

    @Test
    public void whenGetAllTrips_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetTripsByTitle_thenOK() {
        final Trip trip = createRandomTrip();
        createTripAsUri(trip);

        final Response response = RestAssured.get(API_ROOT + "/trip/" + trip.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedTripById_thenOK() {
        final Trip trip = createRandomTrip();
        final String location = createTripAsUri(trip);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(trip.getTitle(), response.jsonPath()
            .get("title"));
    }

    @Test
    public void whenGetNotExistTripById_thenNotFound() {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewTrip_thenCreated() {
        final Trip trip = createRandomTrip();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(trip)
            .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidTrip_thenError() {
        final Trip trip = createRandomTrip();
        trip.setStart(new Date());

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(trip)
            .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedTrip_thenUpdated() {
        final Trip trip = createRandomTrip();
        final String location = createTripAsUri(trip);

        trip.setId(Long.parseLong(location.split("api/trips/")[1]));
        trip.setStart(new Date());
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(trip)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        
        final String title = response.jsonPath().get("title");
        System.out.println("---> " + title);
        assertEquals("newTitle", response.jsonPath()
            .get("title"));

    }

    @Test
    public void whenDeleteCreatedTrip_thenOk() {
        final Trip trip = createRandomTrip();
        final String location = createTripAsUri(trip);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // ===============================

    private Trip createRandomTrip() {
        final Trip trip = new Trip();
        trip.setTitle(randomAlphabetic(10));
        trip.setStart(new Date());
        
        Accomodation a = new Accomodation();
        
        trip.getDestinations().add("New York City");
        trip.getDestinations().add("Moncton");
        
        return trip;
    }

    private String createTripAsUri(Trip trip) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(trip)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
            .get("id");
    }

}
