package edu.mta.groupa.planner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

@Controller
public class TripController {
 
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private TripService service;
    
    @Autowired
    private TripValidator tripValidator;
    
    
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
    	trip.setId(id);
    	tripValidator.validate(trip, result);
    	
        if (result.hasErrors()) {
        	model.addAttribute("trip", trip);
            return "edit-trip";
        }   
        service.update(trip);

        return "redirect:/trip/" + id;
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTrip(@PathVariable("id") long id) {
    	service.delete(id);

        return "redirect:/";
    }
    
    @GetMapping("/add")
    public String showTripForm(Model model, @ModelAttribute("currUser") User user) {
    	Trip trip = new Trip();
    	trip.setUserID(user.getId());
        model.addAttribute("trip", trip);
        return "add-trip";
    }
    
    @PostMapping("/add/{id}")
    public String addTrip(Model model, @PathVariable("id") long id, @Valid Trip trip, 
    		BindingResult result) {
    	
    	tripValidator.validate(trip, result);
    	
    	if (result.hasErrors()) {
            model.addAttribute("trip", trip);
            return "add-trip";
        }
    	service.add(trip);

        return "redirect:/";
    }
    
    @GetMapping("/trip/{id}/print")
    public String getPrintable(@PathVariable("id") long id, Model model) {
    	model.addAttribute("trip", tripRepository.findById(id).get() );
        return "print-trip";
    }
    
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, "start", new CustomDateEditor(dateFormat, true));
    	binder.registerCustomEditor(Date.class, "end", new CustomDateEditor(dateFormat, true));
    }
}
