package com.example.GTC.comment;

import com.example.GTC.comment.model.GetCommentRes;
import com.example.GTC.comment.model.PostCommentReq;
import com.example.GTC.comment.model.PostCommentRes;
import com.example.GTC.config.BaseResponse;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.GTC.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 list 조회 api , 최신순, 페이징 구현 완료
    // ex. /api/v1/comments/{feedId}/{userId}?page=(0부터 시작)&size=(보여지는 개수)
    // 보는 유저의 좋아요 유무 t/f 로 반/
    @ApiOperation(value = "댓글 list 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "피드 식별자", required = true, dataType = "Long", paramType = "path", example = "0"),
            @ApiImplicitParam(name = "userId", value = "로그인된 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    })

    @GetMapping("/{feedId}/{userId}")
    public BaseResponse<List<GetCommentRes>> getCommentResList(@PathVariable Long feedId, @PathVariable Long userId, Pageable pageable) {
        if(pageable.isUnpaged()){
            return new BaseResponse<>(NOT_PAGED);
        }
        if (pageable.getPageSize() != 10) {
            return new BaseResponse<>(INVALID_SIZE);
        }
        return new BaseResponse<>(commentService.getComments(feedId, userId, pageable));

    }

    //댓글 생성 api
    @ApiOperation(value = "댓글 생성")
    @PostMapping("")
    public BaseResponse<PostCommentRes> createComment(@RequestBody PostCommentReq postCommentReq) {
        return new BaseResponse<>(commentService.createComment(postCommentReq));
    }

    //댓글 삭제 api
    @ApiOperation(value = "댓글 삭제")
    @ApiImplicitParam(name = "commentId", value = "댓글 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @DeleteMapping("/{commentId}")
    public BaseResponse<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new BaseResponse<>("삭제가 완료되었습니다.");
    }
}
