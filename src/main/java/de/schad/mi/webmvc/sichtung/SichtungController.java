package de.schad.mi.webmvc.sichtung;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * SichtungController
 */
@Controller
@SessionAttributes(names = {"sichtungen"})
public class SichtungController {

	Logger log = LoggerFactory.getLogger(SichtungController.class);

	@ModelAttribute("sichtungen")
	public void initListe(Model m) {
		m.addAttribute("sichtungen", new Sichtungen());
	}

	@GetMapping("/sichtung")
	public String getForm(Model m) {
		m.addAttribute("sichtungform", new Sichtung());
		return "sichtung";
	}

	@GetMapping("/sichtung/{nr}")
	public String alterSichtung(@ModelAttribute("sichtungen") Sichtungen sichtungen, @PathVariable int nr, Model m) {

		Sichtung found;

		try {
			found = sichtungen.getList().get(nr);
		} catch(IndexOutOfBoundsException e) {
			//throw new SichtungNotFoundException(String.format("Sichtung mit der Nummer %s konnte nicht gefunden werden", nr));

			//lieber wieder exception schmeißen
			found = sichtungen.getList().get(0);
		}

		m.addAttribute("sichtungform", found);
		sichtungen.getList().remove(nr);

		return "sichtung";
	}

	@PostMapping("/sichtung")
	public String getFormInput(
		@Valid @ModelAttribute("sichtungform") Sichtung sichtung,
		BindingResult result,
		@ModelAttribute("sichtungen") Sichtungen sichtungen,
		Model m) {

		if(result.hasErrors()) {
			log.info("Result Binding has errors or form validation has detected Errors");
			return "sichtung";
		}

		sichtungen.add(sichtung);

		// Clear form
		m.addAttribute("sichtungform", new Sichtung());

		return "sichtung";
	}

	@ExceptionHandler(SichtungNotFoundException.class)
	public String handleSichtungNotFoundError() {
		return "error";
	}
}