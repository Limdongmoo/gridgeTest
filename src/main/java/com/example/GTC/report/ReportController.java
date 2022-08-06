package com.example.GTC.report;

import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.config.BaseResponseStatus;
import com.example.GTC.report.reportModel.PostCommentReportReq;
import com.example.GTC.report.reportModel.PostFeedReportReq;
import com.example.GTC.report.reportModel.ReportOption;
import com.example.GTC.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService feedReportService;
    private final JwtService jwtService;


    @Autowired
    public ReportController(ReportService feedReportService, JwtService jwtService) {
        this.feedReportService = feedReportService;
        this.jwtService = jwtService;
    }

    @ApiOperation(value = "신고 내용 list 조회, list 내용은 ReportOption 에 ENUM 으로 등록되어 있음")
    @GetMapping("")
    public BaseResponse<List<String>> getFeedReportContentList(){
        List<String> strings = Arrays.stream(ReportOption.values()).map(
                ReportOption::getContent).toList();

        return new BaseResponse<>(strings);

    }

    @ApiOperation(value = "게시물 신고하기")
    @PostMapping("")
    public BaseResponse<String> createFeedReport(@RequestBody PostFeedReportReq postFeedReportReq) {
        try {
            if (!Objects.equals((long) jwtService.getUserId(), postFeedReportReq.getUserId())) {
                throw new BaseException(BaseResponseStatus.INVALID_JWT);
            }
            feedReportService.createFeedReport(postFeedReportReq);
            return new BaseResponse<>("신고가 완료되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "댓글 신고하기")
    @PostMapping("/comment")
    public BaseResponse<String> createCommentReport(@RequestBody PostCommentReportReq postCommentReportReq) {
        try {
            if (!Objects.equals((long) jwtService.getUserId(), postCommentReportReq.getUserId())) {
                throw new BaseException(BaseResponseStatus.INVALID_JWT);
            }
            feedReportService.createCommentReport(postCommentReportReq);
            return new BaseResponse<>("신고가 완료되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
