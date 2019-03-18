package edu.mta.groupa.planner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.TripRepository;
import edu.mta.groupa.planner.repository.UserRepository;

@Controller
public class HomeController {

	@Value("${welcome.message}")
    private String message;
    
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/")
    public String welcomePage(Model model) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User currentUser = userRepository.findByEmail(email);
    	
        model.addAttribute("message", message);
        model.addAttribute("trips", tripRepository.findAllByUserIDOrderByStartAsc(currentUser.getId()));

        return "welcome"; //view
    }
}
