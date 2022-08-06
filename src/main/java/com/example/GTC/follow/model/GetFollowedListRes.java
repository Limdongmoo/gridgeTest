package com.example.GTC.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetFollowedListRes {
    private Long followId;
    private Long userId;
    private String imgUrl;
}
