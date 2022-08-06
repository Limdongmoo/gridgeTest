package com.example.GTC.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetChatListRes {
    List<GetChatRes> sendMessageList;
    List<GetChatRes> getMessageList;
}
