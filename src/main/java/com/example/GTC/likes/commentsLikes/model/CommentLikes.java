package com.example.GTC.likes.commentsLikes.model;

import com.example.GTC.comment.model.Comment;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentLikes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
