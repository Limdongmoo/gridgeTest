package com.example.GTC.follow;

import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.follow.model.DeleteFollowRes;
import com.example.GTC.follow.model.GetFollowedListRes;
import com.example.GTC.follow.model.PostFollowReq;
import com.example.GTC.follow.model.PostFollowRes;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    //팔로우하기 api, 대상 유저가 공개 유저인 경우 바로 팔로우 승인, 비공개 유저인 경우 팔료우 승인 대기
    @ApiOperation(value = "팔로우하기 , 대상 유저가 공개 유저인 경우 바로 팔로우 승인, 비공개 유저인 경우 팔료우 승인 대기")
    @PostMapping("")
    public BaseResponse<PostFollowRes> createFollow(@RequestBody PostFollowReq postFollowReq) {
        try {
            return new BaseResponse<>(followService.createFollow(postFollowReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //팔로우 승인 대기중인 팔로우 리스트 조회 api
    @ApiOperation(value = "팔로우 승인 대기중인 팔로우 리스트 조회")
    @ApiImplicitParam(name = "userId", value = "로그인 된 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @GetMapping("/{userId}")
    public BaseResponse<List<GetFollowedListRes>> getAllUnacceptedFollow(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(followService.findAllUnacceptedFollow(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //팔로우 승인 api , 승인 후 팔로우 승인 대기중인 팔로우 리스트 조회 api
    @ApiOperation(value = "팔로우 승인 후 팔로우 승인 대기중인 팔로우 리스트 조회")
    @ApiImplicitParam(name = "followId", value = "팔로우 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @PatchMapping("/{followId}")
    public BaseResponse<List<GetFollowedListRes>> acceptFollow(@PathVariable Long followId) {
        try {
            return new BaseResponse<>(followService.acceptFollow(followId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //팔로우 거절 api, 거절 후 팔로우 승인 대기중인 팔로우 리스트 조회 api
    @ApiOperation(value = "팔로우 거절 후 팔로우 승인 대기중인 팔로우 리스트 조회")
    @ApiImplicitParam(name = "userId", value = "팔로우 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @DeleteMapping("/refuse/{userId}/{followId}")
    public BaseResponse<List<GetFollowedListRes>> refuseFollow(@PathVariable Long followId, @PathVariable Long userId) {
        try {
            return new BaseResponse<>(followService.refuseFollow(followId, userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //팔로우 취소 api
    @ApiOperation(value = "팔로우 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "senderId", value = "팔로우 요청한 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0"),
            @ApiImplicitParam(name = "receiverId", value = "팔로우 받은 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    })

    @DeleteMapping("/{senderId}/{receiverId}")
    public BaseResponse<DeleteFollowRes> cancelFollow(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            return new BaseResponse<>(followService.cancelFollow(senderId, receiverId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
