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

/**
 * UserController handles all user based logic of the Application 
 */
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

    /**
     * getUserDashboard shows the default administration dashboard at base URL /users
     * @param m SpringMVC Model
     * @return returns userdashboard template
     */
    @GetMapping("")
    public String getUserDashboard(Model m) {
        m.addAttribute("users", userService.findAll(Sort.by(Sort.Order.asc("loginname").ignoreCase())));
        return "userdashboard";
    }

    /**
     * filterUserDashboard filters the shown Userlist by substrings
     * 
     * @param m SpringMV model
     * @param searchParam substring to look for in user loginname
     * @return filtered userdashboard template
     */
    @GetMapping(value = "", params = "search")
    public String filterUserDashboard(Model m, @RequestParam("search") String searchParam) {
        logger.info("Searchparam: {}", searchParam);
        m.addAttribute(
                "users", userService.findFiltered(
                    Sort.by(Sort.Order.asc("loginname").ignoreCase()), searchParam)
                );
        return "userdashboard";
    }

    /**
     * getNewUserForm shows at /users/create a basic form for new User creation
     * 
     * @param m SpringMVC Model
     * @return userform template
     */
    @GetMapping("/create")
    public String getNewUserForm(Model m) {
        m.addAttribute("userform", new User());
        return "userform";
    }

    /**
     * newUser takes a POST request at /users/create to create a new User
     * @param user validated UserCreationForm model which will be mapped to {@link User} model in database
     * @param result checks if validation was successfull
     * @param file Avatar for the User
     * @param m SpringMVC Model
     * @return same page if validation failed, on success redirect to userdashboard template
     */
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

    /**
     * deleteUser deletes a given User at URL /users/delete?user={loginname}
     * 
     * @param loginname name of User to delete
     * @param m SpringMVC Model
     * @return userdashboard template
     */
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