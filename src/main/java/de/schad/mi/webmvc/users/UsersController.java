package de.schad.mi.webmvc.users;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public String getUserDashboard(Model m) {
        logger.info("falsch");
        m.addAttribute("users", repository.findAll(Sort.by(Sort.Order.asc("loginname").ignoreCase())));
        return "userdashboard";
    }

    @GetMapping(value = "", params = "search")
    public String filterUserDashboard(Model m, @RequestParam("search") String searchParam) {
        logger.info("Searchparam: {}", searchParam);
        m.addAttribute(
                "users", repository.findAllByLoginnameContainingIgnoreCase(
                    Sort.by(Sort.Order.asc("loginname").ignoreCase()), searchParam)
                );
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
        if (databaseUser.isPresent()) {
            m.addAttribute("usernametaken", "Username is already taken");
            return "userform";
        }

        logger.info(user.toString() + "saved into Database");
        repository.save(user);

        return "redirect:/users";
    }

}