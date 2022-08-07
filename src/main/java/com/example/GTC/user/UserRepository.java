package com.example.GTC.user;

import com.example.GTC.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User u);
    Optional<User> findByUserIdAndStatus(Long id, String Status);

    Optional<User> findByPhoneNumAndStatus(String loginId, String status);

    Optional<User> findByEmailAndStatus(String loginId, String status);

    Optional<User> findByUserNameAndStatus(String loginId, String status);

    Optional<User> findByUserName(String userName);

    List<User> findAllByNameOrderByCreatedDateDesc(String name);

    List<User> findAllByCreatedOrderByCreatedDateDesc(LocalDate created);

    List<User> findAllByStatusOrderByCreatedDateDesc(String status);

    List<User> findAllByOrderByCreatedDateDesc();

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNum(String phoneNum);

    Optional<User> findByUserId(Long userId);


    void delete(User user);
}
