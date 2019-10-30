package com.designdreams.copass.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeService {

	@GetMapping("/")
	public String welcome(Model model) {
		return "index";
	}

	@GetMapping("/home")
	public ModelAndView home() {

		ModelAndView model = new ModelAndView("home");
		model.addObject("message","CUSTOMER");

		return model;
	}
}