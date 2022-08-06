package com.example.GTC.admin.feed;

import com.example.GTC.admin.feed.model.GetAdminFeedRes;
import com.example.GTC.comment.CommentRepository;
import com.example.GTC.config.BaseException;
import com.example.GTC.feed.FeedRepository;
import com.example.GTC.feed.model.Feed;
import com.example.GTC.likes.feedLikes.FeedLikesRepository;
import com.example.GTC.report.FeedReportRepository;
import com.example.GTC.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminFeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FeedReportRepository feedReportRepository;
    private final FeedLikesRepository feedLikesRepository;

    @Autowired
    public AdminFeedService(FeedRepository feedRepository, UserRepository userRepository, CommentRepository commentRepository,
                            FeedReportRepository feedReportRepository, FeedLikesRepository feedLikesRepository) {
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.feedReportRepository = feedReportRepository;
        this.feedLikesRepository = feedLikesRepository;
    }

    public List<GetAdminFeedRes> getAllFeed() {
        List<GetAdminFeedRes> getAdminFeedRes = new ArrayList<>();

        List<Feed> allByOrderByCreatedDateDesc = feedRepository.findAllByOrderByCreatedDateDesc();

        return allByOrderByCreatedDateDesc.stream()
                .map(GetAdminFeedRes::from).toList();
    }

    public List<GetAdminFeedRes> findAllByUsername(List<GetAdminFeedRes> getAdminFeedResList,String username) throws BaseException {
        return getAdminFeedResList.stream()
                .filter(a -> a.getUserName().equals(username)).sorted(Comparator.comparing(GetAdminFeedRes::getCreatedDate)).collect(Collectors.toList());

    }

    public List<GetAdminFeedRes> findAllByCreatedDate(List<GetAdminFeedRes> getAdminFeedResList,LocalDate localDate) {
        return getAdminFeedResList.stream()
                .filter(a -> a.getCreatedDate().toLocalDate().equals(localDate)).sorted(Comparator.comparing(GetAdminFeedRes::getCreatedDate)).collect(Collectors.toList());

    }

    public List<GetAdminFeedRes> findAllByStatus(List<GetAdminFeedRes> getAdminFeedResList,String status) {
        return getAdminFeedResList.stream()
                .filter(a -> a.getStatus().equals(status)).sorted(Comparator.comparing(GetAdminFeedRes::getCreatedDate)).collect(Collectors.toList());
    }
}
