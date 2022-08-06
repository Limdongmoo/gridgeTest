package com.example.GTC.admin.feed.model;

import com.example.GTC.comment.model.GetAdminCommentRes;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.feed.model.FeedImgUrl;
import com.example.GTC.likes.feedLikes.model.GetFeedLikesRes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetAdminFeedRes {
    private Long userId;
    private Long feedId;
    private String userName;
    private String profileImgUrl;
    private long feedLikesCount;
    private long commentsCount;
    private String status;
    private List<String> feedImgUrls;
    private LocalDateTime createdDate;
    private List<GetAdminCommentRes> comments;
    private List<GetFeedLikesRes> likes;

    @Builder
    public GetAdminFeedRes(Long userId, Long feedId, String userName, String profileImgUrl, long feedLikesCount, long commentsCount,String status,
                           List<String> feedImgUrls, LocalDateTime createdDate, List<GetAdminCommentRes> comments, List<GetFeedLikesRes> likes) {
        this.userId = userId;
        this.feedId = feedId;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.feedLikesCount = feedLikesCount;
        this.commentsCount = commentsCount;
        this.status = status;
        this.feedImgUrls = feedImgUrls;
        this.createdDate = createdDate;
        this.comments = comments;
        this.likes = likes;
    }

    public static GetAdminFeedRes from(Feed feed){
        return GetAdminFeedRes.builder()
                .userId(feed.getWriter().getUserId())
                .feedId(feed.getFeedId())
                .userName(feed.getWriter().getUserName())
                .profileImgUrl(feed.getWriter().getImgUrl())
                .feedLikesCount(feed.getLikes().size())
                .commentsCount(feed.getComments().size())
                .status(feed.getStatus())
                .feedImgUrls(feed.getFeedImgUrls().stream().map(FeedImgUrl::getImgUrl).toList())
                .createdDate(feed.getCreatedDate())
                .comments(feed.getComments().stream().map(GetAdminCommentRes::from).toList())
                .likes(feed.getLikes().stream().map(GetFeedLikesRes::from).toList())
                .build();
    }

}
