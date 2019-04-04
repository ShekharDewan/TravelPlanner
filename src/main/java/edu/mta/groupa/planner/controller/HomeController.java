package edu.mta.groupa.planner.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * A controller class that handles requests and operations related
 * to the home page.
 * 
 * @author Jennifer & Maryse
 *
 */
@Controller
public class HomeController {
	/**
	 * The welcome message.
	 */
	@Value("${welcome.message}")
    private String message;
	/**
	 * GET operation which displays the home page.
	 * 
	 * @param model		the model which to add attributes.
	 * @return			the HTML page to be rendered.
	 */
    @GetMapping("/")
    public String welcomePage(Model model) {

        model.addAttribute("message", message);

        return "welcome"; //view
    }
}
