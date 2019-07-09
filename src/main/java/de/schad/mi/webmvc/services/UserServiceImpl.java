package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.UserCreationForm;
import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.UserRepository;

/**
 * Implementation of {@see UserService} interface
 * @author Dennis Schad, Michael Heide
 */
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

    /**
     * findAll calls findAll method in repository to fetch all Users from database
     * @return List of Users in database
     */
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * findAll (sorted) calls findAll method in repository to fetch all Users from database (sorted)
     * @param sort sort order of the result
     * @return sorted List of Users in database
     */
    @Override
    public List<User> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    /**
     * findFiltered fetches filtered result of all Users by param
     * @param sort sort order of the result
     * @param param substring to filter users
     * @return sorted and filtered List of Users in database
     */
    @Override
    public List<User> findFiltered(Sort sort, String param) {
        return repository.findAllByLoginnameContainingIgnoreCase(sort, param);
    }

    /**
     * findById calls findById method in repository to fetch User with given id from database
     * @param username id to look for in database
     * @return filled Optional if User was found in database, else empty Optional
     */
    @Override
    public Optional<User> findById(String username) {
        return repository.findById(username);
    }

    /**
     * delete calls delete method in repository to delete User from database
     * @param user user that will be deleted
     */
    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    /**
     * save adds a new User to database and encodes the userpassword
     * @param user User to be added to database
     */
    @Override
    public void save(User user) {
        user.setRole("MEMBER");
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    /**
     * convert converts a UserCreationForm instance into a User instance
     * @param formuser the submitted UserCreationForm instance
     * @param filename preprocessed filename of the image uploaded by the user
     * @return newly created entry in database as User instance
     */
    @Override
    public User convert(UserCreationForm formuser, String filename) {
        User user = new User();
        user.setLoginname(formuser.getLoginname());
        user.setPassword(formuser.getPassword());
        user.setFullname(formuser.getFullname());
        user.setAvatar(filename);
        return user;
    }


}