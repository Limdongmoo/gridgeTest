package com.example.GTC.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostChatRes {
    private Long receiverId;
    private Long senderId;
    private LocalDateTime sendTime;
}
