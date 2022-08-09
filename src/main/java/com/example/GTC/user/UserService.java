package com.example.GTC.user;

import com.example.GTC.auth.model.AuthLoginModel;
import com.example.GTC.config.BaseException;
import com.example.GTC.feed.FeedRepository;
import com.example.GTC.follow.FollowRepository;
import com.example.GTC.user.model.*;
import com.example.GTC.utils.JwtService;
import com.example.GTC.utils.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.GTC.config.BaseResponseStatus.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FollowRepository followRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, FeedRepository feedRepository,
                       FollowRepository followRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.feedRepository = feedRepository;
        this.followRepository = followRepository;
        this.jwtService = jwtService;
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    public User join(User u) {
        return userRepository.save(u);
    }

    public Optional<User> findByEmail(String loginId) {
        return userRepository.findByEmailAndStatus(loginId,"ACTIVE");
    }

    public GetMyPageRes getMyPage(Long userId) {
        Optional<User> optionalUser = userRepository.findByUserIdAndStatus(userId,"ACTIVE");
        User user = optionalUser.get();
        return GetMyPageRes.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .followedCount(followRepository.findAllByFollowedAndAccepted(user, true).size())
                .followingCount(followRepository.findAllByFollowerAndAccepted(user, true).size())
                .feedCount(feedRepository.findAllByUserId(user.getUserId()).size())
                .feedPreviews(feedRepository.findAllByUserId(user.getUserId()).stream()
                        .map(GetMyPageRes::from).toList())
                .imgUrl(user.getImgUrl()).build();
    }

    public GetMyPageRes changeUserPublic(Long userId) {
        User findBy = userRepository.findByUserId(userId).get();
        findBy.setUnveiled(!findBy.getUnveiled());
        User user = userRepository.save(findBy);

        return GetMyPageRes.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .followedCount(followRepository.findAllByFollowedAndAccepted(user, true).size())
                .followingCount(followRepository.findAllByFollowerAndAccepted(user, true).size())
                .feedCount(feedRepository.findAllByUserId(user.getUserId()).size())
                .feedPreviews(feedRepository.findAllByUserId(user.getUserId()).stream()
                        .map(GetMyPageRes::from).toList())
                .imgUrl(user.getImgUrl()).build();
    }

    public GetOthersPageRes getOthersPage(Long ownUserId, Long accessUserId) {
        User ownUser = userRepository.findByUserIdAndStatus(ownUserId,"ACTIVE").get();
        User accessUser = userRepository.findByUserIdAndStatus(accessUserId,"ACTIVE").get();

        if (ownUser.getUnveiled() || followRepository.findByFollowedAndFollower(ownUser, accessUser).isPresent()) {
            return GetOthersPageRes.builder()
                    .name(ownUser.getName())
                    .userName(ownUser.getUserName())
                    .followedCount(followRepository.findAllByFollowedAndAccepted(ownUser, true).size())
                    .followingCount(followRepository.findAllByFollowerAndAccepted(ownUser, true).size())
                    .feedCount(feedRepository.findAllByUserId(ownUser.getUserId()).size())
                    .feedPreviews(feedRepository.findAllByUserId(ownUser.getUserId()).stream()
                            .map(GetMyPageRes::from).toList())
                    .imgUrl(ownUser.getImgUrl()).build();

        } else {
            return GetOthersPageRes.builder()
                    .name(ownUser.getName())
                    .userName(ownUser.getUserName())
                    .followedCount(followRepository.findAllByFollowedAndAccepted(ownUser, true).size())
                    .followingCount(followRepository.findAllByFollowerAndAccepted(ownUser, true).size())
                    .feedCount(feedRepository.findAllByUserId(ownUser.getUserId()).size())
                    .feedPreviews(null)
                    .imgUrl(ownUser.getImgUrl()).build();
        }

    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByUserIdAndStatus(userId,"ACTIVE").get();
        userRepository.delete(user);

    }

    public AuthLoginModel createUser(PostSignupReq postSignupReq) throws BaseException{
        if (userRepository.findByEmail(postSignupReq.getEmail()).isPresent()) {
            throw new BaseException(EXIST_EMAIL);
        }
        if (userRepository.findByUserName(postSignupReq.getUserName()).isPresent()) {
            throw new BaseException(EXIST_USERNAME);
        }
        if (userRepository.findByPhoneNum(postSignupReq.getPhoneNum()).isPresent()) {
            throw new BaseException(EXIST_PHONENUM);
        }

        try {
            User build = User.builder()
                    .birth(postSignupReq.getBirth())
                    .email(postSignupReq.getEmail())
                    .imgUrl(postSignupReq.getImgUrl())
                    .password(SHA256.encrypt(postSignupReq.getPassword()))
                    .name(postSignupReq.getName())
                    .userName(postSignupReq.getUserName())
                    .phoneNum(postSignupReq.getPhoneNum())
                    .build();

            User save = userRepository.save(build);
            return new AuthLoginModel(jwtService.creatJwt(save.getUserId()), save.getUserId());
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CREATE_USER_IN_SERVER);
        }
    }

    public AuthLoginModel emailLoginUser(PostLoginReq postLoginReq) throws BaseException {
        Optional<User> byLoginId = userRepository.findByEmailAndStatus(postLoginReq.getLoginId(), "ACTIVE");
        if (byLoginId.isEmpty()) {
            throw new BaseException(NOT_EXIST_EMAIL);
        } else if (!byLoginId.get().getPassword().equals(SHA256.encrypt(postLoginReq.getPassword()))) {
            throw new BaseException(WRONG_PASSWORD);
        }
        try {
            Long userId = saveLastLoginTime(byLoginId);
            return new AuthLoginModel(jwtService.creatJwt(userId), userId);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_LOGIN_IN_SERVER);
        }
    }

    public AuthLoginModel phoneNumLoginUser(PostLoginReq postLoginReq) throws BaseException {

        Optional<User> byLoginId = userRepository.findByPhoneNumAndStatus(postLoginReq.getLoginId(), "ACTIVE");
        if (byLoginId.isEmpty()) {
            throw new BaseException(NOT_EXIST_PHONENUM);
        } else if (!byLoginId.get().getPassword().equals(SHA256.encrypt(postLoginReq.getPassword()))) {
            throw new BaseException(WRONG_PASSWORD);
        }
        try {
            Long userId = saveLastLoginTime(byLoginId);
            return new AuthLoginModel(jwtService.creatJwt(userId), userId);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_LOGIN_IN_SERVER);
        }

    }


    public AuthLoginModel userNameLoginUser(PostLoginReq postLoginReq) throws BaseException {
        Optional<User> byLoginId = userRepository.findByUserNameAndStatus(postLoginReq.getLoginId(), "ACTIVE");
        if (byLoginId.isEmpty()) {
            throw new BaseException(NOT_EXIST_USERNAME);
        } else if (!byLoginId.get().getPassword().equals(SHA256.encrypt(postLoginReq.getPassword()))) {
            throw new BaseException(WRONG_PASSWORD);
        }
        try {

                Long userId = saveLastLoginTime(byLoginId);
                return new AuthLoginModel(jwtService.creatJwt(userId), userId);

        } catch (Exception e) {
            throw new BaseException(FAILED_TO_LOGIN_IN_SERVER);
        }
    }

    public CheckPhoneNumRes checkPhoneNum(String phoneNum) throws BaseException{
        Optional<User> byLoginId = userRepository.findByPhoneNumAndStatus(phoneNum,"ACTIVE");
        if (byLoginId.isEmpty()) {
            throw new BaseException(NOT_EXIST_PHONENUM);
        }
        try {
            CheckPhoneNumRes checkPhoneNumRes = new CheckPhoneNumRes();
            Long userId = byLoginId.get().getUserId();
            checkPhoneNumRes.setUserId(userId);
            checkPhoneNumRes.setJwt(jwtService.creatJwt(userId));

            return checkPhoneNumRes;
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CHECK_PHONENUMBER_IN_SERVER);
        }
    }

    public PatchPasswordRes changeUserPassword(String password, String phoneNum) throws BaseException {
        Optional<User> byLoginId = userRepository.findByPhoneNumAndStatus(phoneNum,"ACTIVE");
        try {
            User user = byLoginId.get();
            user.setPassword(SHA256.encrypt(password));
            User save = userRepository.save(user);

            return new PatchPasswordRes(save.getUserId(), "비밀번호 변경이 완료되었습니다.");
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_CHANGE_PASSWORD_IN_SERVER);
        }
    }


    private Long saveLastLoginTime(Optional<User> byLoginId) {
        User u = byLoginId.get();
        Long userId = u.getUserId();
        u.setLastLoginTime(LocalDateTime.now());
        userRepository.save(u);
        return userId;
    }

}
