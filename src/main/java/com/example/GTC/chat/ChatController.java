package com.example.GTC.chat;

import com.example.GTC.chat.model.GetChatListRes;
import com.example.GTC.chat.model.GetChatRes;
import com.example.GTC.chat.model.PostChat;
import com.example.GTC.chat.model.PostChatRes;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.GTC.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;
    private final JwtService jwtService;

    @Autowired
    public ChatController(ChatService chatService, JwtService jwtService) {
        this.chatService = chatService;
        this.jwtService = jwtService;
    }

    @ApiOperation(value = "채팅 보내기")
    @PostMapping("")
    public BaseResponse<PostChatRes> sendMessage(@RequestBody PostChat postChat) {
        try {
            Long senderId = Long.valueOf(jwtService.getUserId());

            if (postChat.getMessage().isEmpty()) {
                throw new BaseException(EMPTY_MESSAGE);
            }

            if (postChat.getReceiverId() == null || senderId == null) {
                throw new BaseException(EMPTY_USER_DATA);
            }

            return new BaseResponse<>(chatService.sendChat(postChat, senderId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "받은 채팅 보기")
    @ApiImplicitParam(value = "senderId",required = true, dataType = "Long", paramType = "path",example  = "0")
    @GetMapping("/{senderId}")
    public BaseResponse<GetChatListRes> getMessage(@PathVariable Long senderId) throws BaseException {
        try {
            Long receiverId = Long.valueOf(jwtService.getUserId());

            return new BaseResponse<>(chatService.getChatList(receiverId,senderId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }


    }
}
