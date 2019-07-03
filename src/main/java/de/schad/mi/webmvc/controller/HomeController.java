package de.schad.mi.webmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController is the entrypoint of the web application and shows the landingpage of Track17
 *
 * @author Dennis Schad
 */
@Controller
public class HomeController {

    @GetMapping("")
    public String showLandingPage() {
        return "landingpage";
    }
}