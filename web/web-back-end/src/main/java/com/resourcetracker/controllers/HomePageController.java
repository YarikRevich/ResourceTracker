package com.resourcetracker.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
	@GetMapping("/")
	public String home(Model model) {

		return "home";
	}
}
