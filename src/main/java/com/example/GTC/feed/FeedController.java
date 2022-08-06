package com.example.GTC.feed;

import com.example.GTC.feed.model.GetFeedRes;
import com.example.GTC.feed.model.PostFeedReq;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.config.BaseResponseStatus;
import com.example.GTC.feed.model.PostFeedRes;
import com.example.GTC.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.GTC.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/api/v1/feeds")
@Api(tags = ("Gridge Test Spring Boot REST API"))
public class FeedController {

    private final FeedService feedService;
    private final JwtService jwtService;

    @Autowired
    public FeedController(FeedService feedService, JwtService jwtService) {
        this.feedService = feedService;
        this.jwtService = jwtService;
    }

    //게시물 생성 api
    @ApiOperation(value = "피드 생성")
    @PostMapping("")
    public BaseResponse<PostFeedRes> createFeed(@RequestBody PostFeedReq postFeedReq) throws Exception {
        // 텍스트 길이 check validation
        if (postFeedReq.getText().length() > 1000) {
            throw new BaseException(TOO_LONG_TEXT);
        }

        try {
            Long feedId = feedService.createFeed(postFeedReq);
            PostFeedRes postFeedRes = PostFeedRes.builder()
                    .feedId(feedId)
                    .message("피드 생성이 완료되었습니다.")
                    .build();

            return new BaseResponse<>(postFeedRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //게시물 수정 api
    @ApiOperation(value = "피드 수정")
    @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @PutMapping("/{feedId}")
    public BaseResponse<PostFeedRes> modifyFeed(@PathVariable Long feedId, @RequestBody PostFeedReq postFeedReq) throws BaseException {
        // 텍스트 길이 check validation
        if (postFeedReq.getText().length() > 1000) {
            throw new BaseException(TOO_LONG_TEXT);
        }
        try {
            PostFeedRes postFeedRes = PostFeedRes.builder()
                    .feedId(feedService.modifyFeed(feedId, postFeedReq))
                    .message("피드 수정이 완료되었습니다.")
                    .build();
            return new BaseResponse<>(postFeedRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "피드 삭제")
    @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @DeleteMapping("/{feed_id}")
    public BaseResponse<String> deleteFeed(@PathVariable Long feed_id) throws BaseException {
        try {
            feedService.deleteFeed(feed_id);
            return new BaseResponse<>("삭제가 완료되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //게시물 list 조회 api , 페이징 구현 완료
    // ex. /api/v1/feeds/{userId}?maxNum=(9부터 시작+10*n)&minNum=(0부터 시작 + 10*n)
    //one to many fetch join 메모리 문제로 내부 알고리즘으로 페이징
    // 페이징 처리는 되었지만 최신순 정렬이 안됨
    @ApiOperation(value = "피드 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "로그인된 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0"),
            @ApiImplicitParam(name = "maxNum", value = "게시물의 끝번호 9+10*n", required = true, dataType = "int", paramType = "query", example = "0"),
            @ApiImplicitParam(name = "minNum", value = "게시물 시작 번호 0+10*n", required = true, dataType = "int", paramType = "query", example = "0")

    })
    @GetMapping("/lists/{userId}")
    public BaseResponse<List<GetFeedRes>> getFeedList(@PathVariable Long userId, @RequestParam int maxNum, @RequestParam int minNum) {
        try {
            if (!Long.valueOf(jwtService.getUserId()).equals(userId)) {
                throw new BaseException(INVALID_JWT);
            }
            if (minNum % 10 != 0 || maxNum % 10 != 9) {
                throw new BaseException(INVALID_SIZE);
            }
            List<GetFeedRes> feedList = feedService.getFeedList(userId, maxNum, minNum);
            return new BaseResponse<>(feedList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
