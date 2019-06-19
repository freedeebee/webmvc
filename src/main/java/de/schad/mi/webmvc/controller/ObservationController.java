package de.schad.mi.webmvc.controller;

import javax.validation.Valid;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.services.ImageService;
import de.schad.mi.webmvc.services.ObservationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@SessionAttributes(names = {"sichtungen"})
public class ObservationController {

	private final ImageService imageService;
	private final ObservationService observationService;
	private final Logger logger = LoggerFactory.getLogger(ObservationController.class);

    private String[] daytimecbs = {"morgens", "mittags", "abends"};
    private String[] ratingsbs = {"*", "**", "***", "****", "*****"};


	@Autowired
	public ObservationController(ObservationService observationService, ImageService imageService) {
		this.observationService = observationService;
		this.imageService = imageService;
	}


	@GetMapping("/sichtung")
	public String getForm(Model m) {
		m.addAttribute("sichtungform", new ObservationCreationForm());
		m.addAttribute("daytimevals", daytimecbs);
		m.addAttribute("ratingvals", ratingsbs);
		m.addAttribute("sichtungen", observationService.findAll());
		return "sichtung";
	}

	@GetMapping("/sichtung/{id}")
	public String alterSichtung(@PathVariable long id, Model m) {

		Optional<Observation> found = observationService.findById(id);

		if(found.isPresent()) {
			m.addAttribute("observation", found.get());
			return "observationdetail";
		} else {
			logger.info("Sichtung not found");
			m.addAttribute("sichtungform", new ObservationCreationForm());
			m.addAttribute("sichtungen", observationService.findAll());
			return "sichtung";
		}

	}

	@PostMapping("/sichtung")
	public String getFormInput(
		@Valid @ModelAttribute("sichtungform") ObservationCreationForm sichtung,
		@RequestParam("image") MultipartFile file,
		BindingResult result,
		Model m) {

		if(result.hasErrors()) {
			logger.info("Result Binding has errors or form validation has detected Errors");
			return "sichtung";
		}

		if(!file.isEmpty()) {
            String filename = file.getOriginalFilename();

            String status = "";
            try {
                status = imageService.store(file.getInputStream(), filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            if (status.equals("ok")) {
                sichtung.setImage(file);
            }
        }

		observationService.save(observationService.convert(sichtung));

		// Clear form
		m.addAttribute("sichtungform", new ObservationCreationForm());
		m.addAttribute("daytimevals", daytimecbs);
		m.addAttribute("ratingvals", ratingsbs);
		m.addAttribute("sichtungen", observationService.findAll());
		return "sichtung";
	}


	@ExceptionHandler(SichtungNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleSichtungNotFoundError() {
		return "error";
	}
}