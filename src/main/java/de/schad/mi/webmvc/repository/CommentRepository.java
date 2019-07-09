package de.schad.mi.webmvc.repository;

import de.schad.mi.webmvc.model.data.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CommentRepository makes all CRUD Operations for Comment Model available
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
