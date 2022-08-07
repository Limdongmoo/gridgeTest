package com.example.GTC.user.model;

import com.example.GTC.auth.model.AuthProvider;
import com.example.GTC.chat.model.Chat;
import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.follow.model.Follow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
@DynamicInsert
@Table(name = "user")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(20) default 'USER'")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "domain", columnDefinition = "varchar(20) default 'local'")
    private AuthProvider authProvider;

    @Column(columnDefinition = "varchar(20) default 'ACTIVE'")
    private String status;

    @Column
    private String imgUrl;

    @Column(name = "public", columnDefinition = "boolean default true")
    private Boolean unveiled;

    @Column
    private LocalDateTime lastLoginTime;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate created;

    @JsonIgnore
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Feed> feeds;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> follows = new HashSet<>();

    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Chat> senders = new HashSet<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Chat> receivers = new HashSet<>();

    public String toString() {
        return "User";
    }
}
