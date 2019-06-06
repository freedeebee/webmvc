package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import de.schad.mi.webmvc.model.UserCreationForm;
import de.schad.mi.webmvc.model.data.User;

public interface UserService {

    public List<User> findAll();
    public List<User> findAll(Sort sort);
    public List<User> findFiltered(Sort sort, String param);
    public Optional<User> findById(String username);
    public void delete(User user);
    public void save(User user);
    public User convert(UserCreationForm formuser, String filename);
}