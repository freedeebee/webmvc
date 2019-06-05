package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.encoder = encoder;
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

        user.setRole("MEMBER");
        user.setPassword(encoder.encode(user.getPassword()));
        logger.info("User-Password: {}", user.getPassword());
        repository.save(user);
    }


}