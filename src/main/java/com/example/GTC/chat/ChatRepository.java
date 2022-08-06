package com.example.GTC.chat;

import com.example.GTC.chat.model.Chat;
import com.example.GTC.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat save(Chat chat);
    List<Chat> findBySenderAndReceiver(User sender, User receiver);

    List<Chat> findBySender(User sender);
}
