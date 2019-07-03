package de.schad.mi.webmvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * LoginController handles requestmapping for Login and Logout processes
 *
 * @author Dennis Schad
 */
@Controller
public class LoginController {

	Logger log = LoggerFactory.getLogger(LoginController.class);

	/**
	 * Requestmapping for login page
	 * @return the login page
	 */
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	/**
	 * Postmapping for the login page
	 * @return the login page
	 */
	@PostMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * Postmapping for the logout
	 * @return the landing page
	 */
	@PostMapping("/logout")
	public String logout() {
		return "landingpage";
	}
}