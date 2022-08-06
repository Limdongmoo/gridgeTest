package com.example.GTC.admin.report.model;

import com.example.GTC.report.reportModel.CommentReport;
import com.example.GTC.report.reportModel.FeedReport;
import com.example.GTC.report.reportModel.GetCommentReportRes;
import com.example.GTC.report.reportModel.GetFeedReportRes;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GetReportRes {

    private List<GetFeedReportRes> feedReportRes;
    private List<GetCommentReportRes> commentReportRes;

    @Builder
    public GetReportRes(List<GetFeedReportRes> feedReportRes, List<GetCommentReportRes> commentReportRes) {
        this.feedReportRes = feedReportRes;
        this.commentReportRes = commentReportRes;
    }

    public static GetReportRes from(List<FeedReport> feedReports, List<CommentReport> commentReports) {
        return GetReportRes.builder()
                .feedReportRes(feedReports.stream().map(GetFeedReportRes::from).toList())
                .commentReportRes(commentReports.stream().map(GetCommentReportRes::from).toList()).build();
    }

}
