package edu.mta.groupa.planner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.service.TripService;
import edu.mta.groupa.planner.validator.TripValidator;
/**
 * A controller class that handles Trip object requests and
 * operations.
 * Functionality includes creating, updating, deleting,
 * displaying and printing Trips.
 * 
 * @author Jennifer
 *
 */
@Controller
public class TripController {
	/**
	 * The Trip repository which holds all Trips.
	 */
    @Autowired
    private TripRepository tripRepository;
    /**
     * The Service class which handles Trip operations.
     */
    @Autowired
    private TripService service;
    /**
	 * The Validator class which validates Trip information.
	 */
    @Autowired
    private TripValidator tripValidator;
    /**
     * GET operation which displays the selected Trip page.
     * 
     * @param model		the model which to add attributes.
     * @param id		the current Trip's id.
     * @return			the HTML page to render.
     */
    @GetMapping("/trip/{id}")
    public String tripPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("trip", tripRepository.findById(id).get() );
        return "trip"; //view
    }
    /**
     * GET operation which displays the Trip edit page.
     * 
     * @param id		the current Trip's id.
     * @param model		the model which to add attributes.
     * @return			the HTML page to render.
     */
    @GetMapping("/trip/{id}/edit")
    public String showEditForm(@PathVariable("id") long id, Model model) {
 
        model.addAttribute("trip", tripRepository.findById(id).get());
        return "edit-trip";
    }
    /**
     * POST operation which updates a Trip's information.
     * The Trip object received contains the updated information.
     * If given invalid information, the form is returned with error
     * messages.
     * If successful, redirects to the updated Trip's page.
     * 
     * @param id		the id of the Trip.
     * @param trip		the updated Trip sent by user.
     * @param result	determines whether the information is valid.
     * @param model		the model which to add attributes.
     * @return			the HTML page to be rendered.
     */
    @PostMapping("/trip/{id}/update")
    public String updateTrip(@PathVariable("id") long id, @Valid Trip trip, 
      BindingResult result, Model model) {
    	trip.setId(id);
    	tripValidator.validate(trip, result);
    	
        if (result.hasErrors()) {
        	model.addAttribute("trip", trip);
            return "edit-trip";
        }   
        service.update(trip);

        return "redirect:/trip/" + id;
    }
    /**
     * GET operation which deletes a selected Trip.
     * 
     * @param id	the id of Trip to delete.
     * @return		the HTML page to be rendered.
     */
    @GetMapping("/delete/{id}")
    public String deleteTrip(@PathVariable("id") long id) {
    	service.delete(id);

        return "redirect:/";
    }
    /**
     * GET operation which displays the add Trip page.
     * Creates Trip object for user to fill with data.
     * 
     * @param model		the model which to add attributes.
     * @param user		the current User.
     * @return			the HTML page to be rendered.
     */
    @GetMapping("/add")
    public String showTripForm(Model model, @ModelAttribute("currUser") User user) {
    	Trip trip = new Trip();
    	trip.setUserID(user.getId());
        model.addAttribute("trip", trip);
        return "add-trip";
    }
    /**
     * POST operation which adds a new Trip to the Trip 
     * repository and associates it with the user's account.
     * If issue exists with the given data, returns the form
     * with error messages.
     * If successful, redirects to newly created Trip's page.
     * 
     * @param model		the model which to add attributes.
     * @param id		the Trip's id.
     * @param trip		the Trip object.
     * @param result	determines whether the information is valid.
     * @return			the HTML page to be rendered.
     */
    @PostMapping("/add/{id}")
    public String addTrip(Model model, @PathVariable("id") long id, @Valid Trip trip, 
    		BindingResult result) {
    	
    	tripValidator.validate(trip, result);
    	
    	if (result.hasErrors()) {
            model.addAttribute("trip", trip);
            return "add-trip";
        }
    	service.add(trip);

        return "redirect:/trip/" + trip.getId();
    }
    /**
     * GET operation which displays a printer-friendly version
     * of the current Trip page.
     * 
     * @param id		the Trip's id.
     * @param model		the model which to add attributes.
     * @return			the HTML page to be rendered.
     */
    @GetMapping("/trip/{id}/print")
    public String getPrintable(@PathVariable("id") long id, Model model) {
    	model.addAttribute("trip", tripRepository.findById(id).get() );
        return "print-trip";
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
    	
    	binder.registerCustomEditor(Date.class, "start", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "end", new CustomDateEditor(dateFormat, true));
    	
    	binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
    		@Override
    	    public void processPropertyAccessException(PropertyAccessException ex, 
    	    		  BindingResult bindingResult) {
    			 if (ex.getPropertyName().equals("start") || ex.getPropertyName().equals("end")) {
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
