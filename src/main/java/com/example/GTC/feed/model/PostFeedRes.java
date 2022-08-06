package com.example.GTC.feed.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostFeedRes {
    private String message;
    private Long feedId;

}
