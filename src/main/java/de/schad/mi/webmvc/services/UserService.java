package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import de.schad.mi.webmvc.model.UserCreationForm;
import de.schad.mi.webmvc.model.data.User;

public interface UserService {

    List<User> findAll();
    List<User> findAll(Sort sort);
    List<User> findFiltered(Sort sort, String param);
    Optional<User> findById(String username);
    void delete(User user);
    void save(User user);
    User convert(UserCreationForm formuser, String filename);
}