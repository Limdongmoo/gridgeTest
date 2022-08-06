package com.example.GTC.report;

import com.example.GTC.feed.model.Feed;
import com.example.GTC.report.reportModel.FeedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedReportRepository extends JpaRepository<FeedReport, Long> {
    FeedReport save(FeedReport entity);

    List<FeedReport> findAllByFeed(Feed feed);

    List<FeedReport> findAllByOrderByCreatedDate();

    void deleteByReportId(Long reportId);
}
