package de.schad.mi.webmvc.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import de.schad.mi.webmvc.model.UserCreationForm;
import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.services.ImageService;
import de.schad.mi.webmvc.services.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public UsersController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("")
    public String getUserDashboard(Model m) {
        m.addAttribute("users", userService.findAll(Sort.by(Sort.Order.asc("loginname").ignoreCase())));
        return "userdashboard";
    }

    @GetMapping(value = "", params = "search")
    public String filterUserDashboard(Model m, @RequestParam("search") String searchParam) {
        logger.info("Searchparam: {}", searchParam);
        m.addAttribute(
                "users", userService.findFiltered(
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
            @Valid @ModelAttribute("userform") UserCreationForm user,
            BindingResult result,
            @RequestParam("avatar") MultipartFile file,
            Model m) {

        if(result.hasErrors()) {
            return "userform";
        }

        Optional<User> databaseUser = userService.findById(user.getLoginname());
        if (databaseUser.isPresent()) {
            m.addAttribute("usernametaken", "Username is already taken");
            return "userform";
        }

        String filename = "";

        if(!file.isEmpty()) {
            filename = "avatar-" + user.getLoginname() + "." + file.getOriginalFilename().split("\\.")[1];

            String status = "";
            try {
                status = imageService.store(file.getInputStream(), filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            if (status.equals("ok")) {
                user.setAvatar(file);
            }
        }


        User convertedUser = userService.convert(user, filename);
        userService.save(convertedUser);

        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("user") String loginname, Model m) {

        Optional<User> user = userService.findById(loginname);

        if(user.isPresent()) {
            userService.delete(user.get());
        }

        m.addAttribute("users", userService.findAll(
                Sort.by(Sort.Order.asc("loginname").ignoreCase())));
        return "userdashboard";
    }
}