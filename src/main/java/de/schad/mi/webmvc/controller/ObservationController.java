package de.schad.mi.webmvc.controller;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.model.CommentForm;
import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.services.CommentService;
import de.schad.mi.webmvc.services.ImageService;
import de.schad.mi.webmvc.services.ObservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ObservationController {

	private final ImageService imageService;
	private final ObservationService observationService;
	private final CommentService commentService;
	private final Logger logger = LoggerFactory.getLogger(ObservationController.class);

    private String[] daytimecbs = {"morgens", "mittags", "abends"};
    private String[] ratingsbs = {"*", "**", "***", "****", "*****"};


	@Autowired
	public ObservationController(ObservationService observationService, ImageService imageService, CommentService commentService) {
		this.observationService = observationService;
		this.imageService = imageService;
		this.commentService = commentService;
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
	public String showObservationDetail(@PathVariable long id, Model m) {

		Optional<Observation> found = observationService.findById(id);

		if(found.isPresent()) {
			m.addAttribute("comments", commentService.findAllByObservationOrderByCreatedAtAsc(found.get()));
			m.addAttribute("observation", found.get());
			m.addAttribute("commentform", new CommentForm());
			return "observationdetail";
		} else {
			return "error";
		}

	}

	@GetMapping("/sichtung/{id}/edit")
	public String editObservation(@PathVariable long id, Model m){

		Optional<Observation> found = observationService.findById(id);
		//Simple Edit -> Everyone can edit
		if(found.isPresent()){
			m.addAttribute("sichtungform", observationService.convertBack(found.get()));
			m.addAttribute("daytimevals", daytimecbs);
			m.addAttribute("ratingvals", ratingsbs);
			observationService.delete(found.get());
			return "sichtung";
		}else{
			return "error";
		}
	}

	@PostMapping("/sichtung/{id}/comment")
	public String postComment(
			@PathVariable long id,
			@Valid @ModelAttribute("commentform") CommentForm commentForm,
			BindingResult result,
			Model m,
			Principal principal) {

		if(result.hasErrors()) {
			return "observationdetail";
		}

		Optional<Observation> found = observationService.findById(id);


		if(found.isPresent()) {
			Observation observation = found.get();

			String comment = commentForm.getComment();

			commentService.addComment(comment, principal.getName(), observation);

			m.addAttribute("comments", commentService.findAllByObservationOrderByCreatedAtAsc(observation));
			m.addAttribute("observation", found.get());
			m.addAttribute("commentform", new CommentForm());

			return "observationdetail";
		}

		return "observationdetail";
	}

	@PostMapping("/sichtung")
	public String getFormInput(
		@Valid @ModelAttribute("sichtungform") ObservationCreationForm sichtung, BindingResult result,
		@RequestParam("image") MultipartFile file,
		Model m) {

		if(result.hasErrors()) {
			m.addAttribute("daytimevals", daytimecbs);
			m.addAttribute("ratingvals", ratingsbs);
			return "sichtung";
		}

		String filename = "";

		if(!file.isEmpty()) {
			filename = file.getOriginalFilename().trim() + LocalDateTime.now().toString();

			// Replace special characters
			filename = filename.replace(".", "");
			filename = filename.replace(":", "");

			// Append file format
			filename = filename + "." + file.getOriginalFilename().split("\\.")[1];

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

		observationService.save(observationService.convert(sichtung, filename));

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