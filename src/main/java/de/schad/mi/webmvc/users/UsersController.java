package de.schad.mi.webmvc.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(UsersController.class);

    @GetMapping
    public String getUserDashboard() {
        return "userdashboard";
    }

    @GetMapping("/create")
    public String getNewUserForm() {
        return "userform";
    }
    

    @PostMapping("/create")
    public String newUser(
            @ModelAttribute("userform") User user,
            BindingResult result) {
        logger.info(user.toString());
        return "redirect:/users";
    }

}