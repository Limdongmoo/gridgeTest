package com.example.GTC.follow;

import com.example.GTC.config.BaseException;
import com.example.GTC.follow.model.*;
import com.example.GTC.user.model.User;
import com.example.GTC.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.GTC.config.BaseResponseStatus.*;

@Service
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public PostFollowRes createFollow(PostFollowReq postFollowReq) throws BaseException{
        try {
            if (userRepository.findByUserIdAndStatus(postFollowReq.getFollowReceiver(), "ACTIVE")
                    .get().getUnveiled()) {
                followRepository.save(
                        Follow.builder()
                                .follower(userRepository.findByUserIdAndStatus(postFollowReq.getFollowSender(), "ACTIVE").get())
                                .followed(userRepository.findByUserIdAndStatus(postFollowReq.getFollowReceiver(), "ACTIVE").get())
                                .accepted(true)
                                .build());

            } else {
                followRepository.save(
                        Follow.builder()
                                .follower(userRepository.findByUserIdAndStatus(postFollowReq.getFollowSender(), "ACTIVE").get())
                                .followed(userRepository.findByUserIdAndStatus(postFollowReq.getFollowReceiver(), "ACTIVE").get())
                                .accepted(false)
                                .build());
            }
            return new PostFollowRes(postFollowReq.getFollowReceiver());
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_FOLLOW_IN_SERVER);
        }
    }

    public List<GetFollowedListRes> findAllUnacceptedFollow(Long userId) throws BaseException{
        try {
            User followedUser = userRepository.findByUserIdAndStatus(userId, "ACTIVE").get();
            List<Follow> allByFollowedAndAccepted = followRepository.findAllByFollowedAndAccepted(followedUser, false);
            return allByFollowedAndAccepted.stream().map(
                    a -> new GetFollowedListRes(a.getFollowId(), a.getFollower().getUserId(), a.getFollower().getImgUrl())
            ).toList();
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_LOAD_UNACCEPTED_FOLLOW);
        }
    }

    public List<GetFollowedListRes> acceptFollow(Long followId) throws BaseException {
        try {
            Follow follow = followRepository.findByFollowId(followId).get();

            follow.setAccepted(true);
            Follow save = followRepository.save(follow);

            User followed = follow.getFollowed();
            List<Follow> allByFollowedAndAccepted = followRepository.findAllByFollowedAndAccepted(followed, false);
            return allByFollowedAndAccepted.stream().map(
                    a -> new GetFollowedListRes(a.getFollowId(), a.getFollower().getUserId(), a.getFollower().getImgUrl())
            ).toList();
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_ACCEPT_FOLLOW_IN_SERVER);
        }


    }

    public List<GetFollowedListRes> refuseFollow(Long followId,Long userId) throws BaseException {
        try {
            followRepository.deleteByFollowId(followId);

            User followed = userRepository.findByUserIdAndStatus(userId, "ACTIVE").get();

            List<Follow> allByFollowedAndAccepted = followRepository.findAllByFollowedAndAccepted(followed, false);
            return allByFollowedAndAccepted.stream().map(
                    a -> new GetFollowedListRes(a.getFollowId(), a.getFollower().getUserId(), a.getFollower().getImgUrl())
            ).toList();
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_REFUSEFOLLOW_IN_SERVER);
        }
    }

    public DeleteFollowRes cancelFollow(Long senderId, Long receiverId) throws BaseException{
        try {
            User follower = userRepository.findByUserIdAndStatus(senderId, "ACTIVE").get();
            User followed = userRepository.findByUserIdAndStatus(receiverId, "ACTIVE").get();

            Follow follow = followRepository.findByFollowedAndFollower(followed, follower).get();
            followRepository.deleteByFollowId(follow.getFollowId());
            return new DeleteFollowRes(receiverId);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CALCELFOLLOW_IN_SERVER);
        }
    }
}
