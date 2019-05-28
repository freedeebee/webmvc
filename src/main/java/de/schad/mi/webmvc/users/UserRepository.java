package de.schad.mi.webmvc.users;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{

	List<User> findAllByLoginnameContainingIgnoreCase(Sort sort, String param);

}