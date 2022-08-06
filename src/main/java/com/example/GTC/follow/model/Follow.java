package com.example.GTC.follow.model;

import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Follow extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_follower")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_follwed")
    private User followed;

    @Column(name = "state", columnDefinition = "boolean default true")
    private Boolean accepted;
}
