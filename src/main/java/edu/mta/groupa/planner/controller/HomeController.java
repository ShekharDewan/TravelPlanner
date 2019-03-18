package edu.mta.groupa.planner.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Value("${welcome.message}")
    private String message;

    @GetMapping("/")
    public String welcomePage(Model model) {

        model.addAttribute("message", message);

        return "welcome"; //view
    }
}
