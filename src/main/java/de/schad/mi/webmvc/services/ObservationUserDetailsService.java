package de.schad.mi.webmvc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.UserRepository;

/**
 * UserDetailsService handel some detail Loading
 */
@Service
public class ObservationUserDetailsService implements UserDetailsService{

	private final UserRepository repository;

	/**
	 * Constructor for ObservationUserDetailService
	 * 
	 * @param repository jpa Databasebinding for User Repository
	 */
	@Autowired
	public ObservationUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * loadUserByUsername method find a user in database and show their details
	 * 
	 * @param username Name of the user who wants to be shown
	 * @return Userdetails of a user
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findById(username);

		if(!user.isPresent()) throw new UsernameNotFoundException(username);

		return org.springframework.security.core.userdetails.User.withUsername(username)
			.password(user.get().getPassword()) // encoded!
			.roles(user.get().getRole()) // Rolle könnte auch aus DB kommen
			.build();
	}

}