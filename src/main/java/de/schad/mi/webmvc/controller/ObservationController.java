package de.schad.mi.webmvc.controller;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.model.CommentForm;
import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.ImageMeta;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.services.CommentService;
import de.schad.mi.webmvc.services.ImageService;
import de.schad.mi.webmvc.services.ImageServiceImpl;
import de.schad.mi.webmvc.services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Controller
public class ObservationController {

	private final ImageService imageService;
	private final ObservationService observationService;
	private final CommentService commentService;

	private String[] daytimecbs = { "morgens", "mittags", "abends" };
	private String[] ratingsbs = { "*", "**", "***", "****", "*****" };

	@Autowired
	public ObservationController(ObservationService observationService, ImageService imageService,
			CommentService commentService) {
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

		Observation found = observationService.findById(id)
				.orElseThrow(() -> new SichtungNotFoundException("Sichtung not found"));

		m.addAttribute("comments", found.getComments());
		m.addAttribute("observation", found);
		m.addAttribute("commentform", new CommentForm());
		return "observationdetail";

	}

	@GetMapping("/sichtung/{id}/edit")
	public String editObservation(@PathVariable long id, Model m) {

		Optional<Observation> found = observationService.findById(id);

		if (found.isPresent()) {
			m.addAttribute("sichtungform", observationService.convertBack(found.get()));
			m.addAttribute("daytimevals", daytimecbs);
			m.addAttribute("ratingvals", ratingsbs);
			observationService.delete(found.get());
			return "sichtung";
		} else {
			return "error";
		}
	}

	@PostMapping("/sichtung/{id}")
	public String postComment(@PathVariable long id, @Valid @ModelAttribute("commentform") CommentForm commentForm,
			BindingResult result, Model m, Principal principal) {

		if (result.hasErrors()) {
			return "observationdetail";
		}

		Observation found = observationService.findById(id)
				.orElseThrow(() -> new SichtungNotFoundException("Sichtung not found"));

		String comment = commentForm.getComment();

		commentService.addComment(comment, principal.getName(), found);

		m.addAttribute("comments", found.getComments());
		m.addAttribute("observation", found);
		m.addAttribute("commentform", new CommentForm());

		return "observationdetail";

	}

	@PostMapping("/sichtung")
	public String getFormInput(@Valid @ModelAttribute("sichtungform") ObservationCreationForm sichtung,
			BindingResult result, @RequestParam("image") MultipartFile file, Model m, Principal principal) {

		if (result.hasErrors()) {
			m.addAttribute("daytimevals", daytimecbs);
			m.addAttribute("ratingvals", ratingsbs);
			return "sichtung";
		}

		String filename = "";

		if (!file.isEmpty()) {
			filename = file.getOriginalFilename().trim() + LocalDateTime.now().toString();

			// Replace special characters
			filename = filename.replace(".", "");
			filename = filename.replace(":", "");

			// Append file format
			filename = filename + "." + file.getOriginalFilename().split("\\.")[1];

			String status = "";
			ImageMeta metadata=null;
            try {
				status = imageService.store(file.getInputStream(), filename);
				String[] pathSplit = status.split(" ");
				metadata = imageService.getMetaData(new FileInputStream(pathSplit[1]));
				
            } catch (IOException | ImageProcessingException e) {
                e.printStackTrace();
            }

            if (status.contains("ok")) {
				sichtung.setImage(file);
				if(metadata != null){

				sichtung.setLocation(metadata.getGeoLocation());
				sichtung.setDate(metadata.getDate());
				}
            }
        }

		observationService.save(observationService.convert(principal.getName(), sichtung, filename));

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