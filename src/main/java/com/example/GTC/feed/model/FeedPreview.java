package com.example.GTC.feed.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedPreview {
    private Long feedId;
    private List<String> imgUrl;
}
