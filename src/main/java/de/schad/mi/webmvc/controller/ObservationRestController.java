package de.schad.mi.webmvc.controller;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.services.CommentService;
import de.schad.mi.webmvc.services.ObservationService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ObservationRestController represents the REST endpoint of the application
 *
 * Requestmapping : /rest
 *
 * @author Dennis Schad, Michael Heide, Robin Schmidt
 */
@RestController
@RequestMapping("/rest")
public class ObservationRestController {

    private final ObservationService observationService;
    private final CommentService commentService;

    public ObservationRestController(ObservationService observationService, CommentService commentService) {
        this.observationService = observationService;
        this.commentService = commentService;
    }

    /**
     * GetMapping /sichtungen handles entrypoint request for observations
     *
     * @return List with URLs to all observation objects in database
     */
    @GetMapping("/sichtungen")
    public List<String> getAllObservations() {
        List<Observation> observations = observationService.findAll();

        List<String> urls = new ArrayList<>();

        for(Observation observation: observations) {
            long id = observation.getId();
            String url = String.format("/rest/sichtungen/%s", id);
            urls.add(url);
        }

        return urls;
    }

    /**
     * GetMapping to fetch an observation with given id
     *
     * Throws 404 Error if observation with given id is not found
     *
     * @param id Id of observation to search for
     * @return observation object with given id
     */
    @GetMapping("/sichtungen/{id}")
    public Observation getObservationById(@PathVariable long id) {
        return observationService.findById(id)
                .orElseThrow(() -> new SichtungNotFoundException("Observation not found"));
    }

    /**
     * PostMapping to create a new observation
     *
     * @param observation takes JSON and maps it to Observation object
     * @return new created observation
     */
    @PostMapping(value = "/sichtungen")
    public Observation post(@RequestBody Observation observation) {
        observationService.save(observation);
        return observation;
    }

    /**
     * PutMapping tries to alter a given observation
     *
     * Throws 404 if observation with given id was not found
     *
     * @param id Id of observation to alter
     * @param observation new observation body
     * @return altered observation
     */
    @PutMapping(value = "/sichtungen/{id}")
    public Observation put(@PathVariable long id, @RequestBody Observation observation) {
        observationService.findById(id)
            .orElseThrow( () -> new SichtungNotFoundException("Observation not Found"));
        observationService.override(id, observation);
        return observation;
    }

    /**
     * DeleteMapping deletes given observation if found
     *
     * Throws 404 error if observation with given id was not found
     *
     * @param id Id of observation to delete
     */
    @DeleteMapping(value = "/sichtungen/{id}")
    public void delete(@PathVariable long id) {

        Observation found = observationService.findById(id)
            .orElseThrow(() -> new SichtungNotFoundException("Observation not found"));
        observationService.delete(found);
    }

    /**
     * GetMapping to /sichtungen/{id}/kommentare
     * Finds all comments of a given observation
     *
     * Throws 404 error if observation with given id was not found
     *
     * @param id Id of observation to get the comments from
     * @return List of all comments of given observation
     */
    @GetMapping("/sichtungen/{id}/kommentare")
    public List<Comment> getAllCommentsByGivenId(@PathVariable long id) {
        Observation observation = observationService.findById(id)
        .orElseThrow(() -> new SichtungNotFoundException("Observation not found"));
        return observation.getComments();
    }

    /**
     * GetMapping to /sichtungen/{id}/kommentare/{kid} fetches a given comment
     * of a given observation
     *
     * Throws 404 error if either observation id or commentId was not found
     * @param id Id of observation to look for
     * @param commentId Id of comment to look for
     * @return specific comment of given observation
     */
    @GetMapping("/sichtungen/{id}/kommentare/{kid}")
    public Comment getCommentByObservationAndCommentId(@PathVariable("id") long id,
                                                       @PathVariable("kid") long commentId) {
        // TODO: Bugfix to associate commentId with observation. Current Status: throws 500
        Observation observation = observationService.findById(id).orElseThrow(
                () -> new SichtungNotFoundException("Observation not found"));
        return commentService.findById(commentId).orElseThrow(
                () -> new SichtungNotFoundException("Comment not found"));
    }

    /**
     * PostMapping to /sichtungen/{id}/kommentare creates a new comment for a given observation
     *
     * Throws 404 error if given observation was not found
     *
     * @param id Id of observation to post a new comment to
     * @param comment the actual comment object {@link Comment}
     */
    @PostMapping("/sichtungen/{id}/kommentare")
    public void addComment(@PathVariable("id") long id, @RequestBody Comment comment){

        Observation observation = observationService.findById(id)
            .orElseThrow(() -> new SichtungNotFoundException("Observation not found"));

        commentService.save(comment);
    }

}
