package com.example.GTC.report;

import com.example.GTC.comment.CommentRepository;
import com.example.GTC.config.BaseException;
import com.example.GTC.feed.FeedRepository;
import com.example.GTC.report.reportModel.*;
import com.example.GTC.user.UserRepository;
import com.example.GTC.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static com.example.GTC.config.BaseResponseStatus.*;

@Service
@Transactional
public class ReportService {
    private final FeedReportRepository feedReportRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final CommentReportRepository commentReportRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ReportService(FeedReportRepository feedReportRepository, UserRepository userRepository, FeedRepository feedRepository, CommentReportRepository commentReportRepository, CommentRepository commentRepository) {
        this.feedReportRepository = feedReportRepository;
        this.userRepository = userRepository;
        this.feedRepository = feedRepository;
        this.commentReportRepository = commentReportRepository;
        this.commentRepository = commentRepository;
    }

    public void createFeedReport(PostFeedReportReq postFeedReportReq) throws BaseException {
        if (feedRepository.findFeedByFeedIdAndStatus(postFeedReportReq.getFeedId(), "ACTIVE").get().getWriter().getUserId().equals(postFeedReportReq.getUserId())) {
            throw new BaseException(CANNOT_REPORT_OWN_FEED);
        }
        try {
            FeedReport build = FeedReport.builder()
                    .feed(feedRepository.findFeedByFeedIdAndStatus(postFeedReportReq.getFeedId(), "ACTIVE").get())
                    .sender(userRepository.findByUserIdAndStatus(postFeedReportReq.getUserId(), "ACTIVE").get())
                    .reportOption(Arrays.stream(ReportOption.values()).filter(
                            a -> a.getContent().equals(postFeedReportReq.getContent())
                    ).findAny().get())
                    .build();
            feedReportRepository.save(build);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_FEEDREPORT_IN_SERVER);
        }
    }

    public void createCommentReport(PostCommentReportReq postCommentReportReq) throws BaseException{
        if (commentRepository.findById(postCommentReportReq.getCommentId()).get().getUser().getUserId().equals(postCommentReportReq.getUserId())) {
            throw new BaseException(CANNOT_REPORT_OWN_FEED);
        }
        try {
            CommentReport build = CommentReport.builder()
                    .comment(commentRepository.findById(postCommentReportReq.getCommentId()).get())
                    .sender(userRepository.findByUserIdAndStatus(postCommentReportReq.getUserId(), "ACTIVE").get())
                    .reportOption(Arrays.stream(ReportOption.values()).filter(
                            a -> a.getContent().equals(postCommentReportReq.getContent())
                    ).findAny().get())
                    .build();
            commentReportRepository.save(build);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_COMMENTREPORT_IN_SERVER);
        }

    }
}
