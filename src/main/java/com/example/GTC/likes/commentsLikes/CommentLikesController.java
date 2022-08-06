package com.example.GTC.likes.commentsLikes;

import com.example.GTC.config.BaseResponse;
import com.example.GTC.likes.commentsLikes.model.PostCommentLikesReq;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/commentLikes")
public class CommentLikesController {

    private final CommentLikesService commentLikesService;

    @Autowired
    public CommentLikesController(CommentLikesService commentLikesService) {
        this.commentLikesService = commentLikesService;
    }


    //댓글 좋아요
    // 좋아요 한 상태 ->  좋아요 삭제
    // 좋아요 하지 않은 상태 -> 좋아요 생성
    @ApiOperation(value = "댓글을 좋아요 한 상태에서는 좋아요 삭제, 좋아요 하지 않은 상태에서는 좋아요 생성")
    @PostMapping("")
    public BaseResponse<String> createOrDeleteCommentLikes(@RequestBody PostCommentLikesReq postCommentLikesReq) {
        commentLikesService.createOrDeleteCommentLikes(postCommentLikesReq.getUserId(), postCommentLikesReq.getCommentId());
        return new BaseResponse<>("완료");
    }
}
