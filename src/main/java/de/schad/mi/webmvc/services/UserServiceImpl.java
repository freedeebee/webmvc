package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<User> findFiltered(Sort sort, String param) {
        return repository.findAllByLoginnameContainingIgnoreCase(sort, param);
    }

    @Override
    public Optional<User> findById(String username) {
        return repository.findById(username);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    
}