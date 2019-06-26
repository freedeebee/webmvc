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

@RestController
@RequestMapping("/rest")
public class ObservationRestController {

    private final ObservationService observationService;
    private final CommentService commentService;

    public ObservationRestController(ObservationService observationService, CommentService commentService) {
        this.observationService = observationService;
        this.commentService = commentService;
    }

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

    @GetMapping("/sichtungen/{id}")
    public Observation getObservationById(@PathVariable long id) {
        return observationService.findById(id)
                .orElseThrow(() -> new SichtungNotFoundException("Observation not found"));
    }

    @PostMapping(value = "/{id}")
    public Observation post(@PathVariable long id, @RequestBody Observation observation) {
        observation.setId(id);
        observationService.save(observation);
        return observation;
    }

    @PutMapping(value = "/{id}")
    public Observation put(@PathVariable long id, @RequestBody Observation observation) {
        observationService.override(id, observation);
        return observation;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {

        Optional<Observation> found = observationService.findById(id);
        observationService.delete(found.get());
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

    @PostMapping("sichtungen/{id}/kommentare")
    public void addComment(@PathVariable("id") long id, @RequestBody Comment comment){
        commentService.save(comment);
    }

}
