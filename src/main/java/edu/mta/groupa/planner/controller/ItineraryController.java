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

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.service.ItineraryService;
import edu.mta.groupa.planner.validator.ItineraryValidator;
/**
 * A controller class that handles Itinerary requests and
 * operations.
 * Functionality includes creating, updating, and deleting
 * Itineraries.
 * 
 * @author Jennifer
 *
 */
@Controller
public class ItineraryController {
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
	 * The Validator class which validates Itinerary information.
	 */
	@Autowired
    private ItineraryValidator itineraryValidator;
	/**
     * The Service class which handles Itinerary operations.
     */
	@Autowired 
	private ItineraryService service;
	/**
     * GET operation which displays the add Itinerary page.
     * Creates Itinerary object for user to fill with data.
     * 
     * @param id		the current Trip's id.
     * @param model		the model which to add attributes.
     * @return			the HTML page to render.
     */
	@GetMapping("/trip/{id}/itinerary/add")
    public String showItineraryForm(@PathVariable("id") long id, Model model) {
 
    	Itinerary itinerary = new Itinerary();
    	Trip trip = tripRepository.findById(id).get();
        model.addAttribute("itinerary", itinerary);
        model.addAttribute("trip", trip);
        return "add-itinerary";
    }
	/**
	 * POST operation which adds a new Itinerary to the 
     * current Trip.
     * If issue exists with the given data, returns the form
     * with error messages.
     * If successful, redirects to Trip page.
	 * 
	 * @param model		the model which to add attributes.
	 * @param id		the Trip's id.
	 * @param itinerary	the Itinerary object.
	 * @param result	determines whether the information is valid.
	 * @return			the HTML page to be rendered.
	 */
	@PostMapping("/trip/{id}/itinerary/add")
    public String addItinerary(Model model, @PathVariable("id") long id, @Valid Itinerary itinerary, BindingResult result) {

    	Trip trip = tripRepository.findById(id).get();
    	itinerary.setTrip(trip);
    	
    	itineraryValidator.validate(itinerary, result);
		
		if (result.hasErrors()) {
    		model.addAttribute("itinerary", itinerary);
    		model.addAttribute("trip", trip);
            return "add-itinerary";
        }          
		service.add(trip, itinerary);
		
        return "redirect:/trip/" + id; //view
    }
	/**
	 * GET operation which deletes a selected Itinerary.
	 * 
	 * @param id			the Trip's id.
	 * @param itineraryID	the Itinerary's id.
	 * @param model			the model which to add attributes.
	 * @return				the HTML page to be rendered.
	 */
	 @GetMapping("/trip/{id}/itinerary/delete/{itineraryID}")
	 public String deleteItinerary(@PathVariable("id") long id, 
	    		@PathVariable("itineraryID") long itineraryID, Model model) {

		 	service.delete(id, itineraryID);

	        return "redirect:/trip/" + id;
	  }
	 /**
	  * GET operation which displays the Itinerary edit page.
	  * 
	  * @param id			the Trip's id.
	  * @param itineraryID	the Itinerary's id.
	  * @param model		the model which to add attributes.
	  * @return				the HTML page to be rendered.
	  */
	 @GetMapping("/trip/{id}/itinerary/edit/{itineraryID}")
	 public String showItineraryEditForm(@PathVariable("id") long id, 
	    		@PathVariable("itineraryID") long itineraryID, Model model) {
	    	Trip trip = tripRepository.findById(id).get();
	    	Itinerary itinerary = eManager.find(Itinerary.class, itineraryID); 
	    	model.addAttribute("itinerary", itinerary);
	    	model.addAttribute("trip", trip);
	    	return "edit-itinerary";
	  }
	 /**
	  * POST operation which updates an Itinerary's information.
      * The Itinerary object received contains the updated information.
      * If given invalid information, the form is returned with error
      * messages.
      * If successful, redirects to the Trip page.
	  * 
	  * @param id			the Trip's id.
	  * @param itineraryID	the Itinerary's id.
	  * @param itinerary	the updated Itinerary.
	  * @param result		determines whether the information is valid.
	  * @param model		the model which to add attributes.
	  * @return				the HTML page to be rendered.
	  */
	 @PostMapping("/trip/{id}/itinerary/update/{itineraryID}")
	 public String updateItinerary(@PathVariable("id") long id, 
	    		@PathVariable("itineraryID") long itineraryID, @Valid Itinerary itinerary, 
	      BindingResult result, Model model) {
	    	
	    	Trip trip = tripRepository.findById(id).get();
	    	itinerary.setID(itineraryID);
	    	itinerary.setTrip(trip);
	    	itineraryValidator.validate(itinerary, result);
	    	
	        if (result.hasErrors()) {
	        	model.addAttribute("itinerary", itinerary);
	    		model.addAttribute("trip", trip);
	            return "edit-itinerary";
	        }
	        service.update(trip, itineraryID, itinerary);
	   
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
	    	binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(dateFormat, true));
	    	
	    	binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
	    		@Override
	    	    public void processPropertyAccessException(PropertyAccessException ex, 
	    	    		  BindingResult bindingResult) {
	    			 if (ex.getPropertyName().equals("date")) {
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
