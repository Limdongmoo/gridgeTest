package com.example.GTC.chat.model;

import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_sender")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_receiver")
    private User receiver;

    @Column
    private String message;

}
