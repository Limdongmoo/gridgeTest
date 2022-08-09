package com.example.GTC.feed.model;

import com.example.GTC.comment.model.Comment;
import com.example.GTC.likes.feedLikes.model.FeedLikes;
import com.example.GTC.report.reportModel.FeedReport;
import com.example.GTC.user.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Feed extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Column
    private String text;

    @Column(columnDefinition = "varchar(20) default 'ACTIVE'")
    private String status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate created;

    @OneToMany(mappedBy = "feed",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<FeedImgUrl> feedImgUrls = new HashSet<>();

    @OneToMany(mappedBy = "feed",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "feed",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<FeedLikes> likes = new HashSet<>();

    @OneToMany(mappedBy = "feed",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<FeedReport> feedReports = new HashSet<>();

    public String toString() {
        return "User";
    }
}
