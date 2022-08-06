package com.example.GTC.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentReq {
    private Long userId;
    private Long feedId;
    private String text;
}
