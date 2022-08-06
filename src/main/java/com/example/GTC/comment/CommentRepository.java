package com.example.GTC.comment;

import com.example.GTC.comment.model.Comment;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByFeedOrderByCreatedDateDesc(Feed feed);

    List<Comment> findAllByFeedOrderByCreatedDateDesc(Feed feed, Pageable pageable);

    Comment save(Comment comment);

    void deleteByCommentId(Long commentId);

    Optional<Comment> findByUser(User user);

}
