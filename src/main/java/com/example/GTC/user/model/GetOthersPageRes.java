package com.example.GTC.user.model;

import com.example.GTC.feed.model.Feed;
import com.example.GTC.feed.model.FeedImgUrl;
import com.example.GTC.feed.model.FeedPreview;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class GetOthersPageRes {
    private String name;
    private String userName;
    private long followingCount;
    private long followedCount;
    private long feedCount;
    private List<FeedPreview> feedPreviews;
    private String imgUrl;

    @Builder
    public GetOthersPageRes(String name, String userName, long followingCount, long followedCount, long feedCount, List<FeedPreview> feedPreviews, String imgUrl) {
        this.name = name;
        this.userName = userName;
        this.followingCount = followingCount;
        this.followedCount = followedCount;
        this.feedCount = feedCount;
        this.feedPreviews = feedPreviews;
        this.imgUrl = imgUrl;
    }

    public static FeedPreview from(Feed feed) {
        return FeedPreview.builder()
                .feedId(feed.getFeedId())
                .imgUrl(feed.getFeedImgUrls().stream().map(
                        FeedImgUrl::getImgUrl).toList()
                ).build();
    }
}