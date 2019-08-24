package com.designdreams.copass.service;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeService {

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		//model.put("message", this.message);
		return "index";
	}

}