package com.example.GTC.comment.model;

import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.likes.commentsLikes.model.CommentLikes;
import com.example.GTC.report.reportModel.CommentReport;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "comment",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CommentReport> commentReports = new HashSet<>();

    @OneToMany(mappedBy = "comment",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CommentLikes> commentLikes = new HashSet<>();
}
