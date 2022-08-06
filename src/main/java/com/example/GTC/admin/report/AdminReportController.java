package com.example.GTC.admin.report;

import com.example.GTC.admin.report.model.GetReportRes;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final AdminReportService adminReportService;

    @Autowired
    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    @ApiOperation(value = "관리자 : 신고 내역 조회")
    @GetMapping("")
    public BaseResponse<GetReportRes> getAllReports() {
        return new BaseResponse<>(adminReportService.findAllReports());
    }

    @ApiOperation(value = "관리자 : 피드의 신고를 삭제")
    @ApiImplicitParam(name = "reportId", required = true, dataType = "Long", paramType = "path",example  = "0")
    @DeleteMapping("/feed/{reportId}")
    public BaseResponse<String> deleteFeedReport(@PathVariable Long reportId) {
        adminReportService.deleteFeedReport(reportId);
        return new BaseResponse<>("피드 신고가 삭제되었습니다.");
    }

    @ApiOperation(value = "관리자 : 댓글의 신고를 삭제")
    @ApiImplicitParam(name = "CommentId", required = true, dataType = "Long", paramType = "path",example  = "0")
    @DeleteMapping("/comment/{commentId}")
    public BaseResponse<String> deleteCommentReport(@PathVariable Long commentId) {
        adminReportService.deleteCommentReport(commentId);
        return new BaseResponse<>("댓글 신고가 삭제되었습니다.");
    }

    @ApiOperation(value = "관리자 : 신고된 피드를 삭제")
    @ApiImplicitParam(name = "feedId", required = true, dataType = "Long", paramType = "path",example  = "0")
    @DeleteMapping("/feed/delete/{feedId}")
    public BaseResponse<String> deleteFeed(@PathVariable Long feedId) {
        adminReportService.deleteFeed(feedId);
        return new BaseResponse<>("피드가 삭제되었습니다.");
    }

    @ApiOperation(value = "관리자 : 신고된 댓글을 삭제")
    @ApiImplicitParam(name = "commentId", required = true, dataType = "Long", paramType = "path",example  = "0")
    @DeleteMapping("/comment/delete/{commentId}")
    public BaseResponse<String> deleteComment(@PathVariable Long commentId) {
        adminReportService.deleteComment(commentId);
        return new BaseResponse<>("댓글이 삭제되었습니다.");
    }

}
