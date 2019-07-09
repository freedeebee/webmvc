package de.schad.mi.webmvc.services;

import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;

import java.util.Optional;
/**
 * CommentService Interface show all Methods regarding to handle comments
 * 
 */
public interface CommentService {
    /**
     * addComment to create a new comment in an observation
     * 
     * @param commentText the comment String
     * @param username of the logged in User who wrote a comment
     * @param observation the observation where a comment was made
     */
    void addComment(String commentText, String username, Observation observation);

    /**
     * Search in Database for a special comment
     * 
     * @param id is the Id of a comment
     * @return the comment with the id
     */
    Optional<Comment> findById(long id);
}
