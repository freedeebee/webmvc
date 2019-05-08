package de.schad.mi.webmvc.sichtung;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * SichtungController
 */
@Controller
public class SichtungController {

	@GetMapping("/sichtung")
	public String showPage() {
		return "sichtung";
	}

	@PostMapping("/sichtung")
	public String getFormnput() {
		return "sichtung";
	}
}