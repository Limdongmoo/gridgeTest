package com.example.GTC.admin.feed;

import com.example.GTC.admin.feed.model.GetAdminFeedRes;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.feed.FeedService;
import com.example.GTC.user.UserService;
import com.example.GTC.user.model.Role;
import com.example.GTC.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.GTC.config.BaseResponseStatus.NOT_ADMIN_LOGINED;

@RestController
@RequestMapping("/admin/feeds")
public class AdminFeedController {

    private final JwtService jwtService;
    private final AdminFeedService adminFeedService;
    private final FeedService feedService;
    private final UserService userService;

    @Autowired
    public AdminFeedController(JwtService jwtService, AdminFeedService adminFeedService, FeedService feedService, UserService userService) {
        this.jwtService = jwtService;
        this.adminFeedService = adminFeedService;
        this.feedService = feedService;
        this.userService = userService;
    }

    @ApiOperation(value = "관리자 : 게시물 페이지, 모든 게시물을 최신순으로 정렬")
    @GetMapping("")
    public BaseResponse<List<GetAdminFeedRes>> getAllFeed() {
        try {
            if (!userService.findById((long) jwtService.getUserId()).get().getRole().equals(Role.ADMIN)) {
                throw new BaseException(NOT_ADMIN_LOGINED);
            }
            return new BaseResponse<>(adminFeedService.getAllFeed());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ApiOperation(value = "관리자 : 게시물 조회 필터")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "사용자 이름", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "date", value = "날짜",  dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "게시물 상태", dataType = "String", paramType = "query")
    })
    @GetMapping("/filter")
    public BaseResponse<List<GetAdminFeedRes>> getFilteredFeedList
            (@RequestParam(required = false) String username , @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) LocalDate date,
             @RequestParam(required = false) String status) {
        try {
            if (!userService.findById((long) jwtService.getUserId()).get().getRole().equals(Role.ADMIN)) {
                throw new BaseException(NOT_ADMIN_LOGINED);
            }
            List<GetAdminFeedRes> allFeed = adminFeedService.getAllFeed();
            if (username != null) {
                allFeed = adminFeedService.findAllByUsername(allFeed, username);
                if (date != null) {
                    allFeed = adminFeedService.findAllByCreatedDate(allFeed, date);
                    if (status != null) {
                        return new BaseResponse<>(adminFeedService.findAllByStatus(allFeed, status));
                    }
                    return new BaseResponse<>(allFeed);
                } else {
                    if (status != null) {
                        return new BaseResponse<>(adminFeedService.findAllByStatus(allFeed, status));
                    }
                }
                return new BaseResponse<>(allFeed);
            } else {
                if (date != null) {
                    allFeed = adminFeedService.findAllByCreatedDate(allFeed, date);
                    if (status != null) {
                        return new BaseResponse<>(adminFeedService.findAllByStatus(allFeed, status));
                    }
                    return new BaseResponse<>(allFeed);
                } else {
                    if (status != null) {
                        return new BaseResponse<>(adminFeedService.findAllByStatus(allFeed, status));
                    }
                }
            }
            return new BaseResponse<>(allFeed);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "관리자 : 피드 삭제")
    @ApiImplicitParam(name = "feedId", required = true, dataType = "Long", paramType = "path",example  = "0")
    @DeleteMapping("/{feedId}")
    public BaseResponse<String> deleteFeedByFeedId(@PathVariable Long feedId) {
        try {
            if (!userService.findById((long) jwtService.getUserId()).get().getRole().equals(Role.ADMIN)) {
                throw new BaseException(NOT_ADMIN_LOGINED);
            }
            feedService.deleteFeed(feedId);
            return new BaseResponse<>("해당 게시물이 삭제되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
