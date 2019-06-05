package de.schad.mi.webmvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * LoginController
 */
@Controller
public class LoginController {

	Logger log = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/logout")
	public String logout() {
		return "landingpage";
	}
}