package com.example.GTC.comment;

import com.example.GTC.comment.model.GetCommentRes;
import com.example.GTC.comment.model.PostCommentReq;
import com.example.GTC.comment.model.PostCommentRes;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.log.LogRepository;
import com.example.GTC.log.model.Log;
import com.example.GTC.utils.JwtService;
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
    private final JwtService jwtService;
    private final LogRepository logRepository;

    @Autowired
    public CommentController(CommentService commentService, JwtService jwtService, LogRepository logRepository) {
        this.commentService = commentService;
        this.jwtService = jwtService;
        this.logRepository = logRepository;
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
        try {
            if (pageable.isUnpaged()) {
                throw new BaseException(NOT_PAGED);
            }
            if (pageable.getPageSize() != 10) {
                throw new BaseException(INVALID_SIZE);
            }
            return new BaseResponse<>(commentService.getComments(feedId, userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    //댓글 생성 api
    @ApiOperation(value = "댓글 생성")
    @PostMapping("")
    public BaseResponse<PostCommentRes> createComment(@RequestBody PostCommentReq postCommentReq) {
        try {
            PostCommentRes comment = commentService.createComment(postCommentReq);
            Log log = new Log(true, "Comment", "Create", "댓글 생성", postCommentReq.getUserId());
            logRepository.save(log);
            return new BaseResponse<>(comment);
        } catch (BaseException e) {
            Log log = new Log(false, "Comment", "Create", "댓글 생성", postCommentReq.getUserId());
            logRepository.save(log);
            return new BaseResponse<>(e.getStatus());
        }
    }

    //댓글 삭제 api
    @ApiOperation(value = "댓글 삭제")
    @ApiImplicitParam(name = "commentId", value = "댓글 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @DeleteMapping("/{commentId}")
    public BaseResponse<String> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);

            Log log = new Log(true, "Comment", "Delete", "댓글 삭제",(long)jwtService.getUserId());
            logRepository.save(log);

            return new BaseResponse<>("삭제가 완료되었습니다.");
        } catch (BaseException e) {
            Log log = new Log(false, "Comment", "Delete", "댓글 삭제", null);
            logRepository.save(log);

            return new BaseResponse<>(e.getStatus());
        }
    }
}
