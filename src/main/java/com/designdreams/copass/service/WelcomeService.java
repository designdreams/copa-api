package com.designdreams.copass.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WelcomeService {

	private static final Logger logger = LogManager.getLogger(WelcomeService.class);

	@GetMapping("/")
	public String welcome(Model model) {
		return "index";
	}

	@GetMapping("/home")
	public ModelAndView home(HttpServletRequest request,
							 HttpServletResponse response,
							 @CookieValue(required=false, value = "app_token") String token) {

		if(null == token || token.isEmpty()){

			ModelAndView err_model = new ModelAndView("error");
			return err_model;

		}

		ModelAndView model = new ModelAndView("home");
		//model.addObject("message","CUSTOMER");

		logger.info("request for home");

		return model;
	}

	@GetMapping("/addTrip")
	public ModelAndView addTrip(HttpServletRequest request,
								HttpServletResponse response,
								@CookieValue(required=false, value = "app_token") String token) {


		if(null == token || token.isEmpty()){


			ModelAndView err_model = new ModelAndView("error");
			return err_model;

		}

		ModelAndView model = new ModelAndView("add-trip");
		model.addObject("message","CUSTOMER");
		logger.info("request for add trip");

		return model;
	}

	@GetMapping("/findTrip")
	public ModelAndView findTrip(HttpServletRequest request,
								 HttpServletResponse response,
								 @CookieValue(required=false, value = "app_token") String token) {

		if(null == token || token.isEmpty()){

			ModelAndView err_model = new ModelAndView("error");
			return err_model;

		}

		ModelAndView model = new ModelAndView("find-trip");
		model.addObject("message","CUSTOMER");
		logger.info("request for find trip |{}| -",token);

		return model;
	}

	@GetMapping("/contactUs")
	public ModelAndView contactUs(HttpServletRequest request,
								  HttpServletResponse response) {

		ModelAndView model = new ModelAndView("contact-us");
		model.addObject("message","CUSTOMER");

		logger.info("request for contact us");

		return model;
	}
}