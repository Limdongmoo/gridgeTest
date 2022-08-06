package com.example.GTC.admin.user;

import com.example.GTC.admin.user.model.GetUserRes;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponseStatus;
import com.example.GTC.user.UserRepository;
import com.example.GTC.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminUserService {

    private final UserRepository userRepository;

    @Autowired
    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //모든 유저 조회
    public List<GetUserRes> getAllUser() {
        return userRepository.findAllByOrderByCreatedDateDesc().stream().map(
                GetUserRes::from
        ).toList();
    }

    //username 으로 검색
    public GetUserRes findUserByUserName(String userName) throws BaseException {
            Optional<User> byUserName = userRepository.findByUserName(userName);
            if(byUserName.isPresent()){
                return GetUserRes.from(byUserName.get());
            }
            throw new BaseException(BaseResponseStatus.NOT_EXIST_USERNAME);

    }

    //name 으로 검색
    public List<GetUserRes> findUserByName(String name) throws BaseException {
        List<User> allByNameOrderByCreatedDateDesc = userRepository.findAllByNameOrderByCreatedDateDesc(name);
        if (allByNameOrderByCreatedDateDesc.size() == 0) {
            throw new BaseException(BaseResponseStatus.NOT_EXIST_NAME);
        }
        return allByNameOrderByCreatedDateDesc.stream()
                .map(GetUserRes::from)
                .toList();
    }

    //가입 날짜로 검색
    public List<GetUserRes> findUserByCreatedDate(LocalDate localDate) throws BaseException {
        List<User> allByCreatedDateOrderByCreatedDateDesc = userRepository.findAllByCreatedOrderByCreatedDateDesc(localDate);
        if (allByCreatedDateOrderByCreatedDateDesc.size() == 0) {
            throw new BaseException(BaseResponseStatus.NOT_EXIST_DATE);
        }
        return allByCreatedDateOrderByCreatedDateDesc.stream()
                .map(GetUserRes::from).toList();
    }

    //활성화 상태로 검색
    public List<GetUserRes> findUserByStatus(String status) throws BaseException {
        List<User> allByStatusOrderByCreatedDateDesc = userRepository.findAllByStatusOrderByCreatedDateDesc(status);
        if (allByStatusOrderByCreatedDateDesc.size() == 0) {
            throw new BaseException(BaseResponseStatus.NOT_EXIST_DATE);
        }
        return allByStatusOrderByCreatedDateDesc.stream()
                .map(GetUserRes::from).toList();
    }

    public void inactiveUser(Long userId) throws BaseException {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_USERID);
        }
        byId.get().setStatus("INACTIVE");
        userRepository.save(byId.get());
    }

}
