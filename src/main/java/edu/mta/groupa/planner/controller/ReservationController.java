package edu.mta.groupa.planner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.validator.ReservationValidator;

@Controller
public class ReservationController {

	@PersistenceContext
    private EntityManager eManager;
	
	@Autowired
    private TripRepository tripRepository;
	
	@Autowired
    private ReservationValidator reservationValidator;
	
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
    	
    	Trip trip = tripRepository.findById(id).get();
    	reservation.setTrip(trip);
    	
		reservationValidator.validate(reservation, result);
		
		if (result.hasErrors()) {
    		model.addAttribute("reservation", reservation);
    		model.addAttribute("trip", trip);
            return "add-reservation";
        }
    	
		trip.getReservations().add(reservation);
    	tripRepository.save(trip);
                
        return "redirect:/trip/" + id; //view
    }
    
    @GetMapping("/trip/{id}/reservation/delete/{reservationID}")
    public String deleteReservation(@PathVariable("id") long id, 
    		@PathVariable("reservationID") long reservationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Reservation reservation = eManager.find(Reservation.class, reservationID); 
    	trip.getReservations().remove(reservation);
    	tripRepository.save(trip);
    
        return "redirect:/trip/" + id;
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
    	
    	Trip trip = tripRepository.findById(id).get();
    	reservation.setId(reservationID);
    	reservation.setTrip(trip);
    	reservationValidator.validate(reservation, result);
    	
        if (result.hasErrors()) {
        	model.addAttribute("reservation", reservation);
    		model.addAttribute("trip", trip);
            return "edit-reservation";
        }

        Reservation oldReservation = eManager.find(Reservation.class, reservationID); 

        oldReservation.setTitle(reservation.getTitle());
        oldReservation.setAddress(reservation.getAddress());
        oldReservation.setPrice(reservation.getPrice());
        oldReservation.setConfirmation(reservation.getConfirmation());
        oldReservation.setNotes(reservation.getNotes());
        oldReservation.setDate(reservation.getDate());
        
        tripRepository.save(trip);

        return "redirect:/trip/" + id;
    }
    
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(dateFormat, true));
    }
}
