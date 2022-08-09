package com.example.GTC.feed;

import com.example.GTC.config.BaseException;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.feed.model.FeedImgUrl;
import com.example.GTC.feed.model.GetFeedRes;
import com.example.GTC.feed.model.PostFeedReq;
import com.example.GTC.likes.feedLikes.FeedLikesRepository;
import com.example.GTC.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.GTC.config.BaseResponseStatus.*;

@Service
@Transactional
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FeedLikesRepository feedLikesRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository, UserRepository userRepository, FeedLikesRepository feedLikesRepository) {
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.feedLikesRepository = feedLikesRepository;
    }

    //게시물 생성
    public Long createFeed(PostFeedReq p) throws BaseException {
        try {
            Feed feed = Feed.builder()
                    .writer(userRepository.findByUserIdAndStatus(p.getUserId(),"ACTIVE").get())
                    .text(p.getText())
                    .status("ACTIVE")
                    .build();

            Feed saved = feedRepository.save(feed);

            Optional<Feed> feedByFeedId = feedRepository.findFeedByFeedIdAndStatus(saved.getFeedId(),"ACTIVE");
            feedByFeedId.ifPresent(
                    selectFeed -> {
                        selectFeed.setFeedImgUrls(p.getImgUrls().stream()
                                .map(a -> new FeedImgUrl(feed, a)).collect(Collectors.toSet()));
                        feedRepository.save(selectFeed);
                    }
            );
            return feed.getFeedId();
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_FEED_IN_SERVER);
        }
    }


    //게시물 수정
    public Long modifyFeed(Long feed_id,PostFeedReq p) throws BaseException{
        try {
            Optional<Feed> feedByFeedId = feedRepository.findFeedByFeedIdAndStatus(feed_id,"ACTIVE");
            feedByFeedId.ifPresent(
                    selectFeed -> {
                        selectFeed.setText(p.getText());
                        feedRepository.save(selectFeed);
                    }
            );
            return feed_id;
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_MODIFY_FEED_IN_SERVER);
        }
    }

    public void deleteFeed(Long feed_id) throws BaseException{
        try {
            Optional<Feed> feedByFeedId = feedRepository.findFeedByFeedIdAndStatus(feed_id,"ACTIVE");
            feedByFeedId.ifPresent(
                    feedRepository::delete
            );
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_DELETE_FEED_IN_SERVER);
        }
    }

    public List<GetFeedRes> getFeedList(Long userId, int maxNum, int minNum) {
        List<Feed> allByUserId = feedRepository.findAllByUserIdOrdOrderByCreatedDateDesc(userId);
        List<GetFeedRes> getFeedResListL = new ArrayList<>();
        for (Feed f : allByUserId) {
            if(feedLikesRepository.findByFeedAndUser(f,userRepository.findByUserIdAndStatus(userId,"ACTIVE").get()).isPresent()){
                getFeedResListL.add(GetFeedRes.from(f, true));
            }else{
                getFeedResListL.add(GetFeedRes.from(f));
            }
        }
//        List<GetFeedRes> getFeedResList = allByUserId.stream().map(
//                GetFeedRes::from
//        ).toList();
//        return getFeedResList;
        List<GetFeedRes> result = new ArrayList<>();
        int size = getFeedResListL.size();
        if (size == 0) {
            return result;
        }
        if(size<=10){
            result.addAll(getFeedResListL);
        } else if (size > maxNum) {
            for (int i = minNum; i <= maxNum; i++) {
                result.add(getFeedResListL.get(i));
            }
        }else{
                for(int i=minNum;i<size;i++){
                    result.add(getFeedResListL.get(i));
                }
        }

        return result;
    }

}
