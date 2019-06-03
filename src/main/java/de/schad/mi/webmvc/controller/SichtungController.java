package de.schad.mi.webmvc.controller;

import javax.validation.Valid;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.repository.SichtungRepository;
import de.schad.mi.webmvc.model.data.Sichtung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Optional;

@Controller
@SessionAttributes(names = {"sichtungen"})
public class SichtungController {

	private final SichtungRepository repository;

	@Autowired
	public SichtungController(SichtungRepository repository) {
		this.repository = repository;
	}

	Logger log = LoggerFactory.getLogger(SichtungController.class);

	@GetMapping("/sichtung")
	public String getForm(Model m) {
		m.addAttribute("sichtungform", new Sichtung());
		m.addAttribute("sichtungen", repository.findAll());
		return "sichtung";
	}

	@GetMapping("/sichtung/{id}")
	public String alterSichtung(@PathVariable long id, Model m) {

		Optional<Sichtung> found = repository.findById(id);

		if(found.isPresent()) {
			m.addAttribute("sichtungform", found);
			repository.delete(found.get());
			m.addAttribute("sichtungen", repository.findAll());
			return "sichtung";
		} else {
			m.addAttribute("sichtungen", repository.findAll());
			return "sichtung";
		}
	}

	@PostMapping("/sichtung")
	public String getFormInput(
		@Valid @ModelAttribute("sichtungform") Sichtung sichtung,
		BindingResult result,
		Model m) {

		if(result.hasErrors()) {
			log.info("Result Binding has errors or form validation has detected Errors");
			return "sichtung";
		}

		repository.save(sichtung);

		// Clear form
		m.addAttribute("sichtungform", new Sichtung());
		m.addAttribute("sichtungen", repository.findAll());
		return "sichtung";
	}

	@ExceptionHandler(SichtungNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleSichtungNotFoundError() {
		return "error";
	}
}