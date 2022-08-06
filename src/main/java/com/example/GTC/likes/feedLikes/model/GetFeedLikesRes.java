package com.example.GTC.likes.feedLikes.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GetFeedLikesRes {
    private Long userId;
    private Long feedId;

    @Builder
    public GetFeedLikesRes(Long userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }

    public static GetFeedLikesRes from(FeedLikes feedLikes) {
        return GetFeedLikesRes.builder()
                .feedId(feedLikes.getFeed().getFeedId())
                .userId(feedLikes.getUser().getUserId()).build();

    }

}
