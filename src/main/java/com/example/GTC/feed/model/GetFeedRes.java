package com.example.GTC.feed.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetFeedRes {
    private Long feedId;
    private String nickName;
    private String profileImgUrl;
    private long feedLikesCount;
    private long commentsCount;
    private List<String> feedImgUrls;
    private LocalDateTime createdDate;
    private Boolean liked = false;

    @Builder
    public GetFeedRes(Long feedId, String nickName, String profileImgUrl, long feedLikesCount, long commentsCount, List<String> feedImgUrls, LocalDateTime createdDate, boolean liked) {
        this.feedId = feedId;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.feedLikesCount = feedLikesCount;
        this.commentsCount = commentsCount;
        this.feedImgUrls = feedImgUrls;
        this.createdDate = createdDate;
        this.liked = liked;
    }

    public static GetFeedRes from(Feed feed){
        return GetFeedRes.builder()
                .feedId(feed.getFeedId())
                .nickName(feed.getWriter().getName())
                .profileImgUrl(feed.getWriter().getImgUrl())
                .feedLikesCount(feed.getLikes().size())
                .commentsCount(feed.getComments().size())
                .feedImgUrls(feed.getFeedImgUrls().stream().map(FeedImgUrl::getImgUrl).toList())
                .createdDate(feed.getCreatedDate())
                .build();
    }

    public static GetFeedRes from(Feed feed,boolean b){
        return GetFeedRes.builder()
                .feedId(feed.getFeedId())
                .nickName(feed.getWriter().getName())
                .profileImgUrl(feed.getWriter().getImgUrl())
                .feedLikesCount(feed.getLikes().size())
                .commentsCount(feed.getComments().size())
                .feedImgUrls(feed.getFeedImgUrls().stream().map(FeedImgUrl::getImgUrl).toList())
                .createdDate(feed.getCreatedDate())
                .liked(b)
                .build();
    }


}
