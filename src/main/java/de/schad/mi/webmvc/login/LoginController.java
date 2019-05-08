package de.schad.mi.webmvc.login;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import de.schad.mi.webmvc.login.LoginForm;

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
	public String login(@ModelAttribute("form") LoginForm form, BindingResult result) {

		if (result.hasErrors()) {
			return "login";
		}

		log.info("ModelAttribute has no Errors");

		log.debug("Form Name: " + form.getName());
		log.debug("Form Password" + form.getPassword());

		if ((form.getName() + "!").equals(form.getPassword())) {
			log.info("Login successful from User: " + form.getName());
			return "success";
		}


		return "login";
	}
}