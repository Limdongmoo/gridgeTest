package com.example.GTC.likes.feedLikes;

import com.example.GTC.feed.model.Feed;
import com.example.GTC.likes.feedLikes.model.FeedLikes;
import com.example.GTC.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedLikesRepository extends JpaRepository<FeedLikes,Long> {

    void deleteByFeed(Feed feed);


    void delete(FeedLikes entity);

    Optional<FeedLikes> findByFeedAndUser(Feed f, User user);

    List<FeedLikes> findAllByFeed(Feed feed);
}
