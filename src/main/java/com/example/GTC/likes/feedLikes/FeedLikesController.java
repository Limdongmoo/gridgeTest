package com.example.GTC.likes.feedLikes;

import com.example.GTC.config.BaseResponse;
import com.example.GTC.likes.feedLikes.model.PostFeedLikesReq;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedLikes")
public class FeedLikesController {

    private final FeedLikeService feedLikeService;


    @Autowired
    public FeedLikesController(FeedLikeService feedLikeService) {
        this.feedLikeService = feedLikeService;
    }

    //게시글 좋아요 혹은 삭제
    @ApiOperation(value = "게시글 좋아요 한 상태에서는 좋아요 삭제, 좋아요 하지 않은 상태에서는 좋아요 생성")
    @PostMapping("")
    public BaseResponse<String> createOrDeleteLikes(@RequestBody PostFeedLikesReq postFeedLikesReq) {
        feedLikeService.createOrDeleteLikes(postFeedLikesReq.getUserId(), postFeedLikesReq.getFeedId());
        return new BaseResponse<>("완료");
    }
}
