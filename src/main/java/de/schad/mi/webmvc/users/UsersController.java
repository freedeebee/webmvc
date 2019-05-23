package de.schad.mi.webmvc.users;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String getUserDashboard(Model m) {
        m.addAttribute("users", repository.findAll());
        return "userdashboard";
    }

    @GetMapping("/create")
    public String getNewUserForm(Model m) {
        m.addAttribute("userform", new User());
        return "userform";
    }


    @PostMapping("/create")
    public String newUser(
            @Valid @ModelAttribute("userform") User user,
            BindingResult result,
            Model m) {

        if(result.hasErrors()) {
            return "userform";
        }

        Optional<User> databaseUser = repository.findById(user.getLoginname());
        if (databaseUser != null) {
            m.addAttribute("usernametaken", "Username is already taken");
            return "userform";
        }

        logger.info(user.toString() + "saved into Database");
        repository.save(user);

        return "redirect:/users";
    }

}