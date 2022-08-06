package com.example.GTC.likes.feedLikes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostFeedLikesReq {
    private Long userId;
    private Long feedId;
}
