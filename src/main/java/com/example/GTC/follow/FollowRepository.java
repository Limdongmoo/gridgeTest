package com.example.GTC.follow;

import com.example.GTC.follow.model.Follow;
import com.example.GTC.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFollowedAndAccepted(User followed, boolean accepted);

    List<Follow> findAllByFollowerAndAccepted(User follower, boolean accepted);

    Follow save(Follow follow);

    Optional<Follow> findByFollowId(Long followId);

    Optional<Follow> findByFollowedAndFollower(User followed, User follower);
    void deleteByFollowId(Long followId);


}
