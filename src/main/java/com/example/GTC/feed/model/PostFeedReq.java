package com.example.GTC.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostFeedReq {
    public PostFeedReq() {
    }
    private Long userId;
    private String text;
    private List<String> imgUrls;
}
