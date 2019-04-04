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

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.service.AccommodationService;
import edu.mta.groupa.planner.validator.AccommodationValidator;
/**
 * A controller class that handles Accommodation requests and
 * operations.
 * Functionality includes creating, updating, and deleting
 * Accommodations.
 * 
 * @author Jennifer
 *
 */
@Controller
public class AccommodationController {
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
	 * The Validator class which validates Accommodation information.
	 */
	@Autowired
    private AccommodationValidator accommodationValidator;
	/**
     * The Service class which handles Accommodation operations.
     */
	@Autowired
	private AccommodationService service;
	/**
     * GET operation which displays the add Accommodation page.
     * Creates Accommodation object for user to fill with data.
     * 
     * @param id		the current Trip's id.
     * @param model		the model which to add attributes.
     * @return			the HTML page to render.
     */
	@GetMapping("/trip/{id}/accommodation/add")
    public String showAccomodationForm(@PathVariable("id") long id, Model model) {
 
    	Accommodation accommodation = new Accommodation();
    	accommodation.setAddress(new Address());
    	Trip trip = tripRepository.findById(id).get();
        model.addAttribute("accommodation", accommodation);
        model.addAttribute("trip", trip);
        return "add-accommodation";
    }
	/**
	 * POST operation which adds a new Accommodation to the 
     * current Trip.
     * If issue exists with the given data, returns the form
     * with error messages.
     * If successful, redirects to Trip page.
	 * 
	 * @param model			the model which to add attributes.
	 * @param id			the Trip's id.
	 * @param accommodation	the Accommodation object.
	 * @param result		determines whether the information is valid.
	 * @return				the HTML page to be rendered.
	 */
    @PostMapping("/trip/{id}/accommodation/add")
    public String addAccomodation(Model model, @PathVariable("id") long id, 
    		@Valid Accommodation accommodation, BindingResult result) {

    	Trip trip = tripRepository.findById(id).get(); 	
    	accommodation.setTrip(trip);
    	
		accommodationValidator.validate(accommodation, result);
		
		if (result.hasErrors()) {
    		model.addAttribute("accommodation", accommodation);
    		model.addAttribute("trip", trip);
            return "add-accommodation";
        }
		service.add(trip, accommodation);
	      
        return "redirect:/trip/" + id; //view
    }
    /**
	 * GET operation which deletes a selected Accommodation.
	 * 
	 * @param id				the Trip's id.
	 * @param accommodationID	the Accommodation's id.
	 * @param model				the model which to add attributes.
	 * @return					the HTML page to be rendered.
	 */
    @GetMapping("/trip/{id}/accommodation/delete/{accommodationID}")
    public String deleteAccommodation(@PathVariable("id") long id, 
    		@PathVariable("accommodationID") long accommodationID, Model model) {

    	service.delete(id, accommodationID);
    
        return "redirect:/trip/" + id;
    }
    /**
	 * GET operation which displays the Accommodation edit page.
	 * 
	 * @param id				the Trip's id.
	 * @param accommodationID	the Accommodation's id.
	 * @param model				the model which to add attributes.
	 * @return					the HTML page to be rendered.
	 */
    @GetMapping("/trip/{id}/accommodation/edit/{accommodationID}")
    public String showAccommodationEditForm(@PathVariable("id") long id, 
    		@PathVariable("accommodationID") long accommodationID, Model model) {
    	Trip trip = tripRepository.findById(id).get();
    	Accommodation accommodation = eManager.find(Accommodation.class, accommodationID); 
    	model.addAttribute("accommodation", accommodation);
    	model.addAttribute("trip", trip);
    	return "edit-accommodation";
    }
    /**
	 * POST operation which updates an Accommodation's information.
     * The Accommodation object received contains the updated information.
     * If given invalid information, the form is returned with error
     * messages.
     * If successful, redirects to the Trip page.
	 * 
	 * @param id				the Trip's id.
	 * @param accommodationID	the Accommodation's id.
	 * @param accommodation		the updated Accommodation.
	 * @param result			determines whether the information is valid.
	 * @param model				the model which to add attributes.
	 * @return					the HTML page to be rendered.
	 */
    @PostMapping("/trip/{id}/accommodation/update/{accommodationID}")
    public String updateAccommodation(@PathVariable("id") long id,
    		@PathVariable("accommodationID") long accommodationID, @Valid Accommodation accommodation, 
    		BindingResult result, Model model) {
    	
    	Trip trip = tripRepository.findById(id).get();
    	accommodation.setId(accommodationID);
    	accommodation.setTrip(trip);
    	accommodationValidator.validate(accommodation, result);
    	
    	if (result.hasErrors()) {
    		model.addAttribute("accommodation", accommodation);
    		model.addAttribute("trip", trip);
    		return "edit-accommodation";
    	}
    	service.update(trip, accommodationID, accommodation);

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
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, "checkIn", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "checkOut", new CustomDateEditor(dateFormat, true));
    	
    	binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
    		@Override
    	    public void processPropertyAccessException(PropertyAccessException ex, 
    	    		  BindingResult bindingResult) {
    			 if (ex.getPropertyName().equals("checkIn") || ex.getPropertyName().equals("checkOut")) {
    		          FieldError fieldError = new FieldError(
    		            bindingResult.getObjectName(),
    		            ex.getPropertyName(),
    		            "Invalid date format");

    		          bindingResult.addError(fieldError);
    		        } else {
    		          super.processPropertyAccessException(ex, bindingResult);
    		        } 
    		}
    	});
    }    
}
