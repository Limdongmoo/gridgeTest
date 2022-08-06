package com.example.GTC.feed.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedImgUrl extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgUrlId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_id")
    private Feed feed;
    private String imgUrl;


    public FeedImgUrl(Feed feed, String imgUrl) {
        this.feed = feed;
        this.imgUrl = imgUrl;
    }
}
