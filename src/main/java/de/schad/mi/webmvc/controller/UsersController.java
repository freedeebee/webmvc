package de.schad.mi.webmvc.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.UserRepository;
import de.schad.mi.webmvc.services.UploadService;;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserRepository repository;
    private final UploadService uploadService;

    @Value("${file.upload.directory}")
    private String UPLOADDIR;

    @Autowired
    public UsersController(UserRepository repository, UploadService uploadService) {
        this.repository = repository;
        this.uploadService = uploadService;
    }

    @GetMapping("")
    public String getUserDashboard(Model m) {
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
            @RequestParam("avatar") MultipartFile file,
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

        String filename = "avatar-" + user.getLoginname() + "." + file.getOriginalFilename().split("\\.")[1];

        String status = "";
        try {
            status = uploadService.store(file.getInputStream(), filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Status fileupload: {}", status);

        repository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("user") String loginname, Model m) {

        Optional<User> user = repository.findById(loginname);

        if(user.isPresent()) {
            repository.delete(user.get());
        }

        m.addAttribute("users", repository.findAll(
                Sort.by(Sort.Order.asc("loginname").ignoreCase())));
        return "userdashboard";
    }
}