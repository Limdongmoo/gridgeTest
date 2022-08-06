package com.example.GTC.report;

import com.example.GTC.report.reportModel.CommentReport;
import com.example.GTC.report.reportModel.FeedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    FeedReport save(FeedReport entity);

    List<CommentReport> findAllByOrderByCreatedDate();

    void deleteByReportId(Long reportId);
}
