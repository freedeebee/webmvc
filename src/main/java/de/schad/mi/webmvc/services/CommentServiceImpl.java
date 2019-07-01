package de.schad.mi.webmvc.services;

import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.CommentRepository;
import de.schad.mi.webmvc.repository.ObservationRepository;
import de.schad.mi.webmvc.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ObservationRepository observationRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ObservationRepository observationRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.observationRepository = observationRepository;
    }


    @Override
    public void addComment(String commentText, String username, Observation observation) {

        Optional<User> optionalUser = userRepository.findById(username);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Comment comment = new Comment();
            comment.setComment(commentText);
            comment.setLoginName(user.getLoginname());
            comment.setFullName(user.getFullname());
            comment.setAvatar(user.getAvatar());

            List<Comment> observationComments = observation.getComments();
            observationComments.add(comment);
            observation.setComments(observationComments);
            observationRepository.save(observation);
        }

    }

    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void save(Comment comment, Observation observation) {
        addComment(comment.getComment(), comment.getLoginName(), observation);
    }
}
