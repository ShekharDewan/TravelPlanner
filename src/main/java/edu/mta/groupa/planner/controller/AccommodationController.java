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

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.model.Address;
import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.service.AccommodationService;
import edu.mta.groupa.planner.validator.AccommodationValidator;

@Controller
public class AccommodationController {

	@PersistenceContext
    private EntityManager eManager;
	
	@Autowired
    private TripRepository tripRepository;
	
	@Autowired
    private AccommodationValidator accommodationValidator;
	
	@Autowired
	private AccommodationService service;
	
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
    
    @GetMapping("/trip/{id}/accommodation/delete/{accommodationID}")
    public String deleteAccommodation(@PathVariable("id") long id, 
    		@PathVariable("accommodationID") long accommodationID, Model model) {

    	service.delete(id, accommodationID);
    
        return "redirect:/trip/" + id;
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
    
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, "checkIn", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "checkOut", new CustomDateEditor(dateFormat, true));
    }
    
}
