package com.example.GTC.likes.commentsLikes;

import com.example.GTC.comment.CommentRepository;
import com.example.GTC.comment.model.Comment;
import com.example.GTC.likes.commentsLikes.model.CommentLikes;
import com.example.GTC.user.model.User;
import com.example.GTC.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentLikesService(CommentLikesRepository commentLikesRepository, CommentRepository commentRepository,UserRepository userRepository) {
        this.commentLikesRepository = commentLikesRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public void createOrDeleteCommentLikes(Long userId, Long commentId) {
        User user = userRepository.findByUserIdAndStatus(userId,"ACTIVE").get();
        Comment comment = commentRepository.findById(commentId).get();

        Optional<CommentLikes> findCommentLikes = commentLikesRepository.findByCommentAndUser(comment, user);
        if (findCommentLikes.isPresent()) {
            commentLikesRepository.delete(findCommentLikes.get());
        } else {
            commentLikesRepository.save(CommentLikes.builder()
                    .comment(comment)
                    .user(user).build());
        }
    }
}
