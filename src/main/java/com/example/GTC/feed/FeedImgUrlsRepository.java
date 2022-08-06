//package com.example.GTC.feed;
//
//import com.example.GTC.feed.model.FeedImgUrl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class FeedImgUrlsRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public void saveAll(List<FeedImgUrl> feedImgUrls, LocalDateTime created, LocalDateTime modified ) {
//        jdbcTemplate.batchUpdate("insert into feed_img_url(feed_id,img_url,created_date,modified_date) VALUES(?,?,?,?)",
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement ps, int i) throws SQLException {
//                        ps.setObject(1,feedImgUrls.get(i).getFeed());
//                        ps.setString(2, feedImgUrls.get(i).getImgUrl());
//                        ps.setString(3,created.toString());
//                        ps.setString(4, modified.toString());
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return feedImgUrls.size();
//                    }
//                });
//    }
//
//    public List<Long> findImgUrlIdsByFeedId(Long feed_id) {
//        List<Long> list = new ArrayList<>();
//        return jdbcTemplate.query("select img_url_id as feed_id\n" +
//                        "FROM feed_img_url where feed_id=?\n" +
//                        "\n",
//                (rs, rowNum) -> rs.getLong("feed_id")
//                , feed_id);
//    }
//
//    public void updateFeedImgUrls(List<String> imgUrls, LocalDateTime modified, List<Long> img_url_ids ) {
//
//        jdbcTemplate.batchUpdate("update feed_img_url set img_url=?,modified_date=? WHERE img_url_id=? ",
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement ps, int i) throws SQLException {
//                        ps.setString(1, imgUrls.get(i));
//                        ps.setString(2, modified.toString());
//                        ps.setLong(3, img_url_ids.get(i));
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return imgUrls.size();
//                    }
//                }
//        );
//    }
//
//    public int deleteFeedImgUrlsByFeedId(Long feed_id) {
//        return jdbcTemplate.update("delete FROM feed_img_url WHERE feed_id=?", feed_id);
//    }
//}
