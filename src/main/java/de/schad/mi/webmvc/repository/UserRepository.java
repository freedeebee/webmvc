package de.schad.mi.webmvc.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import de.schad.mi.webmvc.model.data.User;

public interface UserRepository extends JpaRepository<User, String>{

	List<User> findAllByLoginnameContainingIgnoreCase(Sort sort, String param);

}