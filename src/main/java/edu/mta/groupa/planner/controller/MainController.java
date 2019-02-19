package edu.mta.groupa.planner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@Controller
public class MainController {

    @Value("${welcome.message}")
    private String message;
    
    @Autowired
    private TripRepository tripRepository;
    
    @PersistenceContext
    private EntityManager eManager;

    @GetMapping("/")
    public String welcomePage(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("trips", tripRepository.findAllByOrderByStartAsc() );
        
        return "welcome"; //view
    }
    
    @GetMapping("/trip/{id}")
    public String tripPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("trip", tripRepository.findById(id).get() );
        return "trip"; //view
    }
    
    @GetMapping("/trip/{id}/edit")
    public String showEditForm(@PathVariable("id") long id, Model model) {
 
        model.addAttribute("trip", tripRepository.findById(id).get());
        return "edit-trip";
    }
    
    @PostMapping("/trip/{id}/update")
    public String updateTrip(@PathVariable("id") long id, @Valid Trip trip, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-trip";
        }
        Trip oldTrip = tripRepository.findById(id).get();

        oldTrip.setNotes(trip.getNotes());
        oldTrip.setStart(trip.getStart());
        oldTrip.setEnd(trip.getEnd());
        oldTrip.setTitle(trip.getTitle());
        oldTrip.setDescription(trip.getDescription());
        
        tripRepository.save(oldTrip);
        model.addAttribute("trip", tripRepository.findById(id).get()); 
        return "trip";
    }
    
    @GetMapping("/trip/{id}/accommodation/add")
    public String showAccomodationForm(@PathVariable("id") long id, Model model) {
 
    	Accommodation accommodation = new Accommodation();
    	accommodation.setAddress(new Address());
    	Trip trip = tripRepository.findById(id).get();
        model.addAttribute("accommodation", accommodation);
        model.addAttribute("trip", trip);
        return "add-accommodation";
    }
    
    @PostMapping("/trip/{id}/accommodation/add")
    public String addAccomodation(Model model, @PathVariable("id") long id, @Valid Accommodation accommodation, BindingResult result) {
    	if (result.hasErrors()) {
            return "add-accommodation";
        }
    	
    	Trip trip = tripRepository.findById(id).get();
    	trip.getAccommodations().add(accommodation);
    	tripRepository.save(trip);
    	model.addAttribute("trip", trip );
                
        return "trip"; //view
    }
    
    @GetMapping("/trip/{id}/reservation/add")
    public String showReservationForm(@PathVariable("id") long id, Model model) {
 
    	Reservation reservation = new Reservation();
    	reservation.setAddress(new Address());
    	Trip trip = tripRepository.findById(id).get();
        model.addAttribute("reservation", reservation);
        model.addAttribute("trip", trip);
        return "add-reservation";
    }
    
    @PostMapping("/trip/{id}/reservation/add")
    public String addReservation(Model model, @PathVariable("id") long id, @Valid Reservation reservation, BindingResult result) {
    	if (result.hasErrors()) {
            return "add-reservation";
        }
    	
    	Trip trip = tripRepository.findById(id).get();
    	trip.getReservations().add(reservation);
    	tripRepository.save(trip);
    	model.addAttribute("trip", trip );
                
        return "trip"; //view
    }
    
    @GetMapping("/trip/{id}/itinerary/add")
    public String showItineraryForm(@PathVariable("id") long id, Model model) {
 
    	Itinerary itinerary = new Itinerary();
    	Trip trip = tripRepository.findById(id).get();
        model.addAttribute("itinerary", itinerary);
        model.addAttribute("trip", trip);
        return "add-itinerary";
    }
    
    @PostMapping("/trip/{id}/itinerary/add")
    public String addItinerary(Model model, @PathVariable("id") long id, @Valid Itinerary itinerary, BindingResult result) {
    	if (result.hasErrors()) {
            return "add-itinerary";
        }
    	
    	Trip trip = tripRepository.findById(id).get();
    	trip.getItineraries().add(itinerary);
    	tripRepository.save(trip);
    	model.addAttribute("trip", trip );
                
        return "trip"; //view
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTrip(@PathVariable("id") long id) {
    	tripRepository.deleteById(id);
    	// redirect back to root url
        return "redirect:/";
    }
    
    @GetMapping("/trip/{id}/reservation/delete/{reservationID}")
    public String deleteReservation(@PathVariable("id") long id, 
    		@PathVariable("reservationID") long reservationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Reservation reservation = eManager.find(Reservation.class, reservationID); 
    	trip.getReservations().remove(reservation);
    	tripRepository.save(trip);
    	model.addAttribute("trip", trip);
        return "trip";
    }
    
    @GetMapping("/trip/{id}/accommodation/delete/{accommodationID}")
    public String deleteAccommodation(@PathVariable("id") long id, 
    		@PathVariable("accommodationID") long accommodationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Accommodation accommodation = eManager.find(Accommodation.class, accommodationID); 
    	trip.getAccommodations().remove(accommodation);
    	tripRepository.save(trip);
    	model.addAttribute("trip", trip);
        return "trip";
    }
    
    @GetMapping("/trip/{id}/itinerary/delete/{itineraryID}")
    public String deleteItinerary(@PathVariable("id") long id, 
    		@PathVariable("itineraryID") long itineraryID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Itinerary itinerary = eManager.find(Itinerary.class, itineraryID); 
    	trip.getItineraries().remove(itinerary);
    	tripRepository.save(trip);
    	model.addAttribute("trip", trip);
        return "trip";
    }

    @GetMapping("/trip/{id}/itinerary/edit/{itineraryID}")
    public String showItineraryEditForm(@PathVariable("id") long id, 
    		@PathVariable("itineraryID") long itineraryID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Itinerary itinerary = eManager.find(Itinerary.class, itineraryID); 
    	model.addAttribute("itinerary", itinerary);
    	model.addAttribute("trip", trip);
    	return "edit-itinerary";
    }

    @PostMapping("/trip/{id}/itinerary/update/{itineraryID}")
    public String updateItinerary(@PathVariable("id") long id, 
    		@PathVariable("itineraryID") long itineraryID, @Valid Itinerary itinerary, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-itinerary";
        }
        Trip trip = tripRepository.findById(id).get();
        Itinerary oldItinerary = eManager.find(Itinerary.class, itineraryID); 
        oldItinerary.setNotes(itinerary.getNotes());
        oldItinerary.setDate(itinerary.getDate());
        
        tripRepository.save(trip);
        model.addAttribute("trip", tripRepository.findById(id).get()); 
        return "trip";
    }

    @GetMapping("/trip/{id}/reservation/edit/{reservationID}")
    public String showReservationEditForm(@PathVariable("id") long id, 
    		@PathVariable("reservationID") long reservationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Reservation reservation = eManager.find(Reservation.class, reservationID); 
    	model.addAttribute("reservation", reservation);
    	model.addAttribute("trip", trip);
    	return "edit-reservation";
    }

    @PostMapping("/trip/{id}/reservation/update/{reservationID}")
    public String updateReservation(@PathVariable("id") long id, 
    		@PathVariable("reservationID") long reservationID, @Valid Reservation reservation, 
      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-reservation";
        }
        Trip trip = tripRepository.findById(id).get();
        Reservation oldReservation = eManager.find(Reservation.class, reservationID); 
        
        oldReservation.setTitle(reservation.getTitle());
        oldReservation.setAddress(reservation.getAddress());
        oldReservation.setPrice(reservation.getPrice());
        oldReservation.setConfirmation(reservation.getConfirmation());
        oldReservation.setNotes(reservation.getNotes());
        oldReservation.setDate(reservation.getDate());
        
        tripRepository.save(trip);
        model.addAttribute("trip", tripRepository.findById(id).get()); 
        return "trip";
    }

    @GetMapping("/trip/{id}/accommodation/edit/{accommodationID}")
    public String showAccommodationEditForm(@PathVariable("id") long id, 
    		@PathVariable("accommodationID") long accommodationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Accommodation accommodation = eManager.find(Accommodation.class, accommodationID); 
    	model.addAttribute("accommodation", accommodation);
    	model.addAttribute("trip", trip);
    	return "edit-accommodation";
    }

    @PostMapping("/trip/{id}/accommodation/update/{accommodationID}")
    public String updateAccommodation(@PathVariable("id") long id,
    		@PathVariable("accommodationID") long accommodationID, @Valid Accommodation accommodation, 
    		BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		return "edit-accommodation";
    	}
    	Trip trip = tripRepository.findById(id).get();
        Accommodation oldAccommodation = eManager.find(Accommodation.class, accommodationID); 
        
        oldAccommodation.setNotes(accommodation.getNotes());
        oldAccommodation.setAddress(accommodation.getAddress());
        oldAccommodation.setCheckIn(accommodation.getCheckIn());
        oldAccommodation.setCheckOut(accommodation.getCheckOut());
        oldAccommodation.setPrice(accommodation.getPrice());
        oldAccommodation.setTitle(accommodation.getTitle());
        
        tripRepository.save(trip);
        model.addAttribute("trip", tripRepository.findById(id).get()); 
    	return "trip";
    }
    
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, "start", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "end", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "checkIn", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "checkOut", new CustomDateEditor(dateFormat, true));
    }
}