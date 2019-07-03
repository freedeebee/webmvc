package de.schad.mi.webmvc.services;

import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;

import java.util.Optional;

public interface CommentService {

    void addComment(String commentText, String username, Observation observation);
    Optional<Comment> findById(long id);
}
