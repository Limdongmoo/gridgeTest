package com.example.GTC.likes.commentsLikes;

import com.example.GTC.comment.model.Comment;
import com.example.GTC.likes.commentsLikes.model.CommentLikes;
import com.example.GTC.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByUserAndComment(User user, Comment comment);

    @Override
    CommentLikes save(CommentLikes entity);

    Optional<CommentLikes> findByCommentAndUser(Comment comment, User user);

    @Override
    void delete(CommentLikes entity);
}
