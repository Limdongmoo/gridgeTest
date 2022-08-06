package com.example.GTC.report.reportModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCommentReportRes {

    private Long senderId;
    private Long commentId;
    private String reportContent;
    private Long reportId;
    private String text;

    @Builder
    public GetCommentReportRes(Long senderId, Long commentId, String reportContent,String text,Long reportId) {
        this.senderId = senderId;
        this.commentId = commentId;
        this.reportContent = reportContent;
        this.text = text;
        this.reportId = reportId;
    }

    public static GetCommentReportRes from(CommentReport commentReport) {
        return GetCommentReportRes.builder()
                .senderId(commentReport.getSender().getUserId())
                .reportContent(commentReport.getReportOption().getContent())
                .commentId(commentReport.getComment().getCommentId())
                .text(commentReport.getComment().getText())
                .reportId(commentReport.getReportId())
                .build();
    }
}