package edu.mta.groupa.planner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.mta.groupa.planner.repository.TripRepository;

@Controller
public class MainController {

    @Value("${welcome.message}")
    private String message;
    
    @Autowired
    private TripRepository tripRepository;

    @GetMapping("/")
    public String welcomePage(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("trips", tripRepository.findAll() );
        
        return "welcome"; //view
    }
    
    @GetMapping("/trip/{id}")
    public String tripPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("trip", tripRepository.findById(id).get() );
        return "trip"; //view
    }

}