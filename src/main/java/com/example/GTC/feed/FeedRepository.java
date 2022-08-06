package com.example.GTC.feed;

import com.example.GTC.feed.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Override
    Feed save(Feed feed);

    Optional<Feed> findFeedByFeedIdAndStatus(Long feedId,String status);

    @Query(value = "select distinct f FROM Feed f " +
            "LEFT JOIN FETCH f.writer wr " +
            "LEFT JOIN FETCH f.feedImgUrls fiu " +
            "LEFT JOIN FETCH f.likes li " +
            "LEFT JOIN FETCH f.comments co " +
            "where f.writer.userId in (select followed.userId from Follow where follower.userId = :userId)")
    List<Feed> findAllByUserIdOrdOrderByCreatedDateDesc(@Param("userId") Long userId);

    @Query(value = "select distinct f FROM Feed f " +
            "LEFT JOIN FETCH f.writer wr " +
            "LEFT JOIN FETCH f.feedImgUrls fiu " +
            "LEFT JOIN FETCH f.likes li " +
            "LEFT JOIN FETCH f.comments co " +
            "where f.writer.userId in (select followed.userId from Follow where follower.userId = :userId)")
    List<Feed> findAllByUserId(@Param("userId") Long userId);

    List<Feed> findAllByOrderByCreatedDateDesc();


    void delete(Feed feed);

    void deleteByFeedId(Long feedId);


}
