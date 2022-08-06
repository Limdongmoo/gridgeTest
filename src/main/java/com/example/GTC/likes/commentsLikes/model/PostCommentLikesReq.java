package com.example.GTC.likes.commentsLikes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentLikesReq {
    private Long userId;
    private Long commentId;
}
