package de.schad.mi.webmvc.services;

import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;

import java.util.List;

public interface CommentService {

    void addComment(String commentText, String username, Observation observation);
    List<Comment> findAllByObservationOrderByCreatedAtAsc(Observation observation);

}
