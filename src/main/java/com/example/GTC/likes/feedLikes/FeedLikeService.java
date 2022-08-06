package com.example.GTC.likes.feedLikes;

import com.example.GTC.feed.FeedRepository;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.likes.feedLikes.model.FeedLikes;
import com.example.GTC.user.model.User;
import com.example.GTC.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FeedLikeService {

    private final FeedLikesRepository feedLikesRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedLikeService(FeedLikesRepository feedLikesRepository, FeedRepository feedRepository, UserRepository userRepository) {
        this.feedLikesRepository = feedLikesRepository;
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
    }

    public void createOrDeleteLikes(Long userId, Long feedId) {
        Feed feed = feedRepository.findFeedByFeedIdAndStatus(feedId,"ACTIVE").get();
        User user = userRepository.findByUserIdAndStatus(userId,"ACTIVE").get();

        Optional<FeedLikes> findFeed = feedLikesRepository.findByFeedAndUser(feed, user);

        if(findFeed.isPresent()){
            feedLikesRepository.delete(findFeed.get());
        }else{
            feedLikesRepository.save(FeedLikes.builder()
                    .feed(feed)
                    .user(user)
                    .build());
        }

    }
}
