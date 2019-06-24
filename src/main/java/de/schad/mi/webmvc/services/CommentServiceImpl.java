package de.schad.mi.webmvc.services;

import de.schad.mi.webmvc.model.data.Comment;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.model.data.User;
import de.schad.mi.webmvc.repository.CommentRepository;
import de.schad.mi.webmvc.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
            comment.setObservation(observation);
            comment.setAvatar(user.getAvatar());
            commentRepository.save(comment);
        }

    }

    @Override
    public List<Comment> findAllByObservationOrderByCreatedAtAsc(Observation observation) {
        return commentRepository.findAllByObservationOrderByCreatedAtAsc(observation);
    }
}
