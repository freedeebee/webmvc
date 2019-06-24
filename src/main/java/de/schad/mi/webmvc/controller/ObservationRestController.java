package de.schad.mi.webmvc.controller;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.services.CommentService;
import de.schad.mi.webmvc.services.ObservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ObservationRestController {

    private final ObservationService observationService;
    private final CommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(ObservationRestController.class);

    public ObservationRestController(ObservationService observationService, CommentService commentService) {
        this.observationService = observationService;
        this.commentService = commentService;
    }

    @GetMapping("/sichtungen")
    public List<Observation> getAllObservations() {
        return observationService.findAll();
    }

    @GetMapping("/sichtungen/{id}")
    public Observation getObservationById(@PathVariable long id) {
        return observationService.findById(id).orElseThrow(() -> new SichtungNotFoundException("Observation not found"));
    }

    @GetMapping("/sichtungen/{id}/kommentare")
    public List<Comment> getAllCommentsByGivenId(@PathVariable long id) {
        Observation observation = observationService.findById(id).orElseThrow(
                () -> new SichtungNotFoundException("Observation not found"));
        return commentService.findAllByObservation(observation);
    }

    @GetMapping("/sichtungen/{id}/kommentare/{kid}")
    public Comment getCommentByObservationAndCommentId(@PathVariable("id") long id,
                                                       @PathVariable("kid") long commentId) {
        // TODO: Bugfix to associate commentId with observation. Current Status: throws 500
        Observation observation = observationService.findById(id).orElseThrow(
                () -> new SichtungNotFoundException("Observation not found"));
        return commentService.findById(commentId).orElseThrow(
                () -> new SichtungNotFoundException("Comment not found"));
    }

}
