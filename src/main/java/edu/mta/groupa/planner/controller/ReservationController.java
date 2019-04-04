package edu.mta.groupa.planner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.service.ReservationService;
import edu.mta.groupa.planner.validator.ReservationValidator;
/**
 * A controller class that handles Reservation requests and
 * operations.
 * Functionality includes creating, updating, and deleting
 * Reservations.
 * 
 * @author Jennifer
 *
 */
@Controller
public class ReservationController {
	/**
	 * The EntityManager which manages all registered entities.
	 */
	@PersistenceContext
    private EntityManager eManager;
	/**
	 * The Trip repository which holds all Trips.
	 */
	@Autowired
    private TripRepository tripRepository;
	/**
	 * The Validator class which validates Reservation information.
	 */
	@Autowired
    private ReservationValidator reservationValidator;
	/**
     * The Service class which handles Reservation operations.
     */
	@Autowired
	private ReservationService service;
	/**
     * GET operation which displays the add Reservation page.
     * Creates Reservation object for user to fill with data.
     * 
     * @param id		the current Trip's id.
     * @param model		the model which to add attributes.
     * @return			the HTML page to render.
     */
	@GetMapping("/trip/{id}/reservation/add")
    public String showReservationForm(@PathVariable("id") long id, Model model) {
 
    	Reservation reservation = new Reservation();
    	reservation.setAddress(new Address());
    	Trip trip = tripRepository.findById(id).get();
        model.addAttribute("reservation", reservation);
        model.addAttribute("trip", trip);
        return "add-reservation";
    }
	/**
	 * POST operation which adds a new Reservation to the 
     * current Trip.
     * If issue exists with the given data, returns the form
     * with error messages.
     * If successful, redirects to Trip page.
	 * 
	 * @param model			the model which to add attributes.
	 * @param id			the Trip's id.
	 * @param reservation	the Reservation object.
	 * @param result		determines whether the information is valid.
	 * @return				the HTML page to be rendered.
	 */
    @PostMapping("/trip/{id}/reservation/add")
    public String addReservation(Model model, @PathVariable("id") long id, 
    		@Valid Reservation reservation, BindingResult result) {
    	
    	Trip trip = tripRepository.findById(id).get();
    	reservation.setTrip(trip);
    	
		reservationValidator.validate(reservation, result);
		
		if (result.hasErrors()) {
    		model.addAttribute("reservation", reservation);
    		model.addAttribute("trip", trip);
            return "add-reservation";
        }
    	service.add(trip, reservation);
        
        return "redirect:/trip/" + id; //view
    }
    /**
	 * GET operation which deletes a selected Reservation.
	 * 
	 * @param id				the Trip's id.
	 * @param reservationID		the Reservation's id.
	 * @param model				the model which to add attributes.
	 * @return					the HTML page to be rendered.
	 */
    @GetMapping("/trip/{id}/reservation/delete/{reservationID}")
    public String deleteReservation(@PathVariable("id") long id, 
    		@PathVariable("reservationID") long reservationID, Model model) {

    	service.delete(id, reservationID);
    
        return "redirect:/trip/" + id;
    }
    /**
	 * GET operation which displays the Reservation edit page.
	 * 
	 * @param id				the Trip's id.
	 * @param reservationID		the Reservation's id.
	 * @param model				the model which to add attributes.
	 * @return					the HTML page to be rendered.
	 */
    @GetMapping("/trip/{id}/reservation/edit/{reservationID}")
    public String showReservationEditForm(@PathVariable("id") long id, 
    		@PathVariable("reservationID") long reservationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Reservation reservation = eManager.find(Reservation.class, reservationID); 
    	model.addAttribute("reservation", reservation);
    	model.addAttribute("trip", trip);
    	return "edit-reservation";
    }
    /**
	 * POST operation which updates a Reservation's information.
     * The Reservation object received contains the updated information.
     * If given invalid information, the form is returned with error
     * messages.
     * If successful, redirects to the Trip page.
	 * 
	 * @param id				the Trip's id.
	 * @param reservationID		the Reservation's id.
	 * @param reservation		the updated Reservation.
	 * @param result			determines whether the information is valid.
	 * @param model				the model which to add attributes.
	 * @return					the HTML page to be rendered.
	 */
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
        service.update(trip, reservationID, reservation);

        return "redirect:/trip/" + id;
    }
    /**
	 * Configures the web data binder to handle certain formats
     * when processing certain data fields.
	 * 
	 * @param binder 	the WebDataBinder to configure.
	 */
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
    	timeFormat.setLenient(false);
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, "reserveTime", new CustomDateEditor(timeFormat, true));
    	binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(dateFormat, true));
    	
    	binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
    		@Override
    	    public void processPropertyAccessException(PropertyAccessException ex, 
    	    		  BindingResult bindingResult) {
    			 if (ex.getPropertyName().equals("date") || ex.getPropertyName().equals("reserveTime")) {
    				 FieldError fieldError = new FieldError(
    		            bindingResult.getObjectName(),
    		            ex.getPropertyName(),
    		            "Invalid format");

    		          bindingResult.addError(fieldError);
    		        } else {
    		          super.processPropertyAccessException(ex, bindingResult);
    		        } 
    		}
    	});
    }
}
