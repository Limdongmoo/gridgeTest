package com.example.GTC.comment.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCommentRes {
    private Long feedId;
    private String imgUrl;
    private String name;
    private String text;
    private LocalDateTime createdDate;
    private Long commentId;
    private Boolean comment_liked;

    public static GetCommentRes from(Comment comment) {
        return GetCommentRes.builder()
                .feedId(comment.getFeed().getFeedId())
                .imgUrl(comment.getUser().getImgUrl())
                .name(comment.getUser().getName())
                .text(comment.getText())
                .createdDate(comment.getCreatedDate())
                .commentId(comment.getCommentId())
                .comment_liked(false)
                .build();
    }

    public static GetCommentRes likedFrom(Comment comment) {
        return GetCommentRes.builder()
                .feedId(comment.getFeed().getFeedId())
                .imgUrl(comment.getUser().getImgUrl())
                .name(comment.getUser().getName())
                .text(comment.getText())
                .createdDate(comment.getCreatedDate())
                .commentId(comment.getCommentId())
                .comment_liked(true)
                .build();
    }

}
