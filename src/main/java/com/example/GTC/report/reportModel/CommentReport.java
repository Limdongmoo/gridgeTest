package com.example.GTC.report.reportModel;


import com.example.GTC.comment.model.Comment;
import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReport extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Enumerated(EnumType.STRING)
    private ReportOption reportOption;

    public String toString() {
        return "CommentReport";
    }
}

