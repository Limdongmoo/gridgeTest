package com.example.GTC.report.reportModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetFeedReportRes {

    private Long senderId;
    private Long feedId;
    private Long reportId;
    private String reportContent;
    private String text;

    @Builder
    public GetFeedReportRes(Long senderId, Long feedId, Long reportId, String reportContent, String text) {
        this.senderId = senderId;
        this.feedId = feedId;
        this.reportId = reportId;
        this.reportContent = reportContent;
        this.text = text;
    }

    public static GetFeedReportRes from(FeedReport feedReport) {
        return GetFeedReportRes.builder()
                .senderId(feedReport.getSender().getUserId())
                .reportContent(feedReport.getReportOption().getContent())
                .feedId(feedReport.getFeed().getFeedId())
                .reportId(feedReport.getReportId())
                .text(feedReport.getFeed().getText())
                .build();
    }
}
