package com.example.GTC.likes.feedLikes.model;

import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedLikes extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
