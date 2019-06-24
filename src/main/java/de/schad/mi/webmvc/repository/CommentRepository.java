package de.schad.mi.webmvc.repository;

import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByObservationOrderByCreatedAtAsc(Observation observation);
}
