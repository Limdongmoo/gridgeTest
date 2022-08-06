package com.example.GTC.report.reportModel;

import com.example.GTC.feed.model.BaseTime;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.user.model.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedReport extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Enumerated(EnumType.STRING)
    private ReportOption reportOption;
}
