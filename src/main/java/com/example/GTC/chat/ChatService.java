package com.example.GTC.chat;

import com.example.GTC.chat.model.*;
import com.example.GTC.config.BaseException;
import com.example.GTC.user.UserRepository;
import com.example.GTC.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.GTC.config.BaseResponseStatus.*;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {

        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public PostChatRes sendChat(PostChat postChatReq, Long senderId) throws BaseException {
        try {
            User receiver = userRepository.findByUserIdAndStatus(postChatReq.getReceiverId(),"ACTIVE").get();
            User sender = userRepository.findByUserIdAndStatus(senderId,"ACTIVE").get();
            Chat build = Chat.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .message(postChatReq.getMessage())
                    .build();
            Chat save = chatRepository.save(build);
            return new PostChatRes(receiver.getUserId(), senderId, save.getCreatedDate());

        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_CHAT_IN_SERVER);
        }
    }

    public GetChatListRes getChatList(Long receiverId, Long senderId) throws BaseException {
        try {
            User me = userRepository.findByUserIdAndStatus(receiverId,"ACTIVE").get();
            User otherUser = userRepository.findByUserIdAndStatus(senderId,"ACTIVE").get();
            List<Chat> bySenderAndReceiver = chatRepository.findBySenderAndReceiver(otherUser, me);

            GetChatListRes getChatListRes = new GetChatListRes();

            getChatListRes.setGetMessageList(bySenderAndReceiver.stream()
                    .map(a -> new GetChatRes(a.getSender().getUserId(), a.getMessage(), a.getReceiver().getUserId(), false))
                    .toList());

            List<Chat> bySender = chatRepository.findBySenderAndReceiver(me,otherUser);

            getChatListRes.setSendMessageList(bySender.stream()
                    .map(a -> new GetChatRes(a.getSender().getUserId(), a.getMessage(), a.getReceiver().getUserId(), true))
                    .toList());

            return getChatListRes;
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_GET_CHAT_IN_SERVER);
        }
    }
}
