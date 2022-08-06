package com.example.GTC.comment;

import com.example.GTC.comment.model.Comment;
import com.example.GTC.comment.model.GetCommentRes;
import com.example.GTC.comment.model.PostCommentReq;
import com.example.GTC.comment.model.PostCommentRes;
import com.example.GTC.feed.FeedRepository;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.likes.commentsLikes.CommentLikesRepository;
import com.example.GTC.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentLikesRepository commentLikesRepository;

    @Autowired
    public CommentService(FeedRepository feedRepository, CommentRepository commentRepository, UserRepository userRepository, CommentLikesRepository commentLikesRepository) {
        this.feedRepository = feedRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentLikesRepository = commentLikesRepository;
    }

    public List<GetCommentRes> getComments(Long feedId,Long userId,Pageable pageable) {

        Optional<Feed> feed = feedRepository.findFeedByFeedIdAndStatus(feedId,"ACTIVE");
        List<Comment> allByFeed = commentRepository.findAllByFeedOrderByCreatedDateDesc(feed.get(),pageable);
        List<GetCommentRes> getCommentResList = new ArrayList<>();
        for (Comment c : allByFeed) {
            if (commentLikesRepository.findByUserAndComment(userRepository.findByUserIdAndStatus(userId,"ACTIVE").get(), c).isPresent()) {
                getCommentResList.add(GetCommentRes.likedFrom(c));
            } else {
                getCommentResList.add(GetCommentRes.from(c));
            }
        }

        return getCommentResList;
    }

    public PostCommentRes createComment(PostCommentReq postCommentReq) {

        Comment build = Comment.builder()
                .feed(feedRepository.findFeedByFeedIdAndStatus(postCommentReq.getFeedId(),"ACTIVE").get())
                .text(postCommentReq.getText())
                .user(userRepository.findByUserIdAndStatus(postCommentReq.getUserId(),"ACTIVE").get())
                .build();

        Comment save = commentRepository.save(build);
        return new PostCommentRes(save.getFeed().getFeedId());

    }

    public void deleteComment(Long commentId){
        commentRepository.deleteByCommentId(commentId);
    }
}
