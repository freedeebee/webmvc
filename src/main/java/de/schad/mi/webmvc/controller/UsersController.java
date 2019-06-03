package de.schad.mi.webmvc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.UserRepository;;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserRepository repository;

    @Value("${file.upload.directory}")
    private String UPLOADDIR;

    public UsersController(UserRepository repository) {
        this.repository = repository;
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

        String status = abspeichern(file);
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

    private String abspeichern(MultipartFile datei) {

        logger.info(UPLOADDIR);
        // Metainformationen zu Upload auslesen
        String filename = datei.getOriginalFilename();
        long size = datei.getSize();
        String contenttype = datei.getContentType();
        // Dateiinhalt kopieren in Upload-Verzeichnis
        String status;
        try {
            InputStream input = datei.getInputStream();
            Path zielpfad = Paths.get(UPLOADDIR, filename);
            Files.copy(input, zielpfad);
            status = "ok";
        } catch (IOException exc) {
            status = exc.getMessage();
        }
        return String.format("Datei %s (%s Bytes, %s) Status %s", filename, size, contenttype, status);
    }

}