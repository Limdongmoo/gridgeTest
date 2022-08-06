package com.example.GTC.admin.report;

import com.example.GTC.admin.report.model.GetReportRes;
import com.example.GTC.comment.CommentRepository;
import com.example.GTC.feed.FeedRepository;
import com.example.GTC.report.CommentReportRepository;
import com.example.GTC.report.FeedReportRepository;
import com.example.GTC.report.reportModel.CommentReport;
import com.example.GTC.report.reportModel.FeedReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminReportService {
    private final FeedReportRepository feedReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AdminReportService(FeedReportRepository feedReportRepository, CommentReportRepository commentReportRepository, FeedRepository feedRepository, CommentRepository commentRepository) {
        this.feedReportRepository = feedReportRepository;
        this.commentReportRepository = commentReportRepository;
        this.feedRepository = feedRepository;
        this.commentRepository = commentRepository;
    }

    public GetReportRes findAllReports() {
        List<FeedReport> allFeedReports = feedReportRepository.findAllByOrderByCreatedDate();
        List<CommentReport> allCommentReports = commentReportRepository.findAllByOrderByCreatedDate();
        return GetReportRes.from(allFeedReports, allCommentReports);
    }

    public void deleteFeedReport(Long feedReportId) {
        feedReportRepository.deleteByReportId(feedReportId);
    }

    public void deleteCommentReport(Long commentReportId) {
        commentReportRepository.deleteByReportId(commentReportId);
    }

    public void deleteFeed(Long feedId) {
        feedRepository.deleteByFeedId(feedId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteByCommentId(commentId);
    }
}
