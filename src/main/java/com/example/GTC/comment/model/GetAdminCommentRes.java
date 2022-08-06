package com.example.GTC.comment.model;

import com.example.GTC.admin.feed.model.GetAdminFeedRes;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAdminCommentRes {

    private Long userId;
    private String text;
    private LocalDateTime createdDate;
    private Long commentId;

    public static GetAdminCommentRes from(Comment comment) {
        return GetAdminCommentRes.builder()
                .userId(comment.getUser().getUserId())
                .text(comment.getText())
                .createdDate(comment.getCreatedDate())
                .commentId(comment.getCommentId())
                .build();
    }
}
