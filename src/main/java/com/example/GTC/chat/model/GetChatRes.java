package com.example.GTC.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetChatRes {

    private Long sender;
    private String message;
    private Long receiver;
    private Boolean myMessage;

}
