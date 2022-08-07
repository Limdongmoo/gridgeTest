package com.example.GTC.user;

import com.example.GTC.auth.model.AuthLoginModel;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.log.LogRepository;
import com.example.GTC.log.model.Log;
import com.example.GTC.user.model.*;
import com.example.GTC.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.GTC.config.BaseResponseStatus.*;
import static com.example.GTC.utils.ValidationRegex.isRegexEmail;
import static com.example.GTC.utils.ValidationRegex.isRegexPhoneNum;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;
    private JwtService jwtService;
    private LogRepository logRepository;

    @Autowired
    public UserController(UserService userService, JwtService jwtService, LogRepository logRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.logRepository = logRepository;
    }

    // 마이페이지 조회 api
    @ApiOperation(value = "마이페이지 조회")
    @ApiImplicitParam(name = "userId", value = "로그인 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @GetMapping("/{userId}")
    public BaseResponse<GetMyPageRes> getMyPage(@PathVariable Long userId) {
        try {
            if (!Long.valueOf(jwtService.getUserId()).equals(userId)) {
                throw new BaseException(INVALID_JWT);
            }
            return new BaseResponse<>(userService.getMyPage(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //공개 비공개 변경 api
    @ApiOperation(value = "프로필 공개/비공개 변경 api")
    @ApiImplicitParam(name = "userId", value = "로그인 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @PatchMapping("/{userId}")
    public BaseResponse<User> changeUserPublic(@PathVariable Long userId) {
        try {
            if (!Long.valueOf(jwtService.getUserId()).equals(userId)) {
                throw new BaseException(INVALID_JWT);
            }
            Log log = new Log(true, "User", "Update", "프로필 공개/비공개변경", userId);
            logRepository.save(log);
            return new BaseResponse<>(userService.changeUserPublic(userId));
        }catch (BaseException e) {
            Log log = new Log(false, "User", "Update", "프로필 공개/비공개변경", userId);
            logRepository.save(log);
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "다른사람의 페이지 조회 api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ownUserId", value = "다른사람의 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0"),
            @ApiImplicitParam(name = "accessUserId", value = "로그인 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    })
    @GetMapping("/access/{ownUserId}/{accessUserId}")
    public BaseResponse<GetOthersPageRes> getOthersPage(@PathVariable Long ownUserId, @PathVariable Long accessUserId) {
        try {
            if (!Long.valueOf(jwtService.getUserId()).equals(accessUserId)) {
                throw new BaseException(INVALID_JWT);
            }
            return new BaseResponse<>(userService.getOthersPage(ownUserId, accessUserId));
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "유저 삭제")
    @ApiImplicitParam(name = "userId", value = "로그인 유저 식별자", required = true, dataType = "Long", paramType = "path", example = "0")
    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable Long userId) {
        try {
            if (!Long.valueOf(jwtService.getUserId()).equals(userId)) {
                throw new BaseException(INVALID_JWT);
            }
            userService.deleteUser(userId);
            Log log = new Log(true, "User", "Delete", "유저 삭제", userId);
            logRepository.save(log);
            return new BaseResponse<>("삭제가 완료되었습니다.");
        }catch (BaseException e) {
            Log log = new Log(false, "User", "Delete", "유저 삭제", userId);
            logRepository.save(log);
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public BaseResponse<AuthLoginModel> signup(@RequestBody PostSignupReq postSignupReq) {
        try {
            AuthLoginModel user = userService.createUser(postSignupReq);
            Log log = new Log(true, "User", "Create", "회원가입", user.getUserId());
            logRepository.save(log);
            return new BaseResponse<>(user);
        } catch (BaseException e) {
            Log log = new Log(false, "User", "Create", "회원가입", null);
            logRepository.save(log);
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "email 로 로그인")
    @PostMapping("/login/email")
    public BaseResponse<AuthLoginModel> emailLogin(@RequestBody PostLoginReq postLoginReq) {
        try{
            if (!isRegexEmail(postLoginReq.getLoginId())) {
            return new BaseResponse<>(NOT_REGEX_EMAIL);
        }
            return new BaseResponse<>(userService.emailLoginUser(postLoginReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "전화번호로 로그인")
    @PostMapping("/login/phoneNum")
    public BaseResponse<AuthLoginModel> phoneNumLogin(@RequestBody PostLoginReq postLoginReq) {
        try {
            if (!isRegexPhoneNum(postLoginReq.getLoginId())) {
                throw new BaseException(NOT_REGEX_PHONENUM);
            }
            return new BaseResponse<>(userService.phoneNumLoginUser(postLoginReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "username 으로 로그인")
    @PostMapping("/login/username")
    public BaseResponse<AuthLoginModel> localIdLogin(@RequestBody PostLoginReq postLoginReq) {
        try {
            return new BaseResponse<>(userService.userNameLoginUser(postLoginReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "비밀번호 변경을 위한 전화번호 확인")
    @PostMapping("/check/phoneNumber")
    public BaseResponse<CheckPhoneNumRes> checkPhoneNum(@RequestBody CheckPhoneNumForPatchPasswordReq checkPhoneNumForPatchPasswordReq) {
        try {
            return new BaseResponse<>(userService.checkPhoneNum(checkPhoneNumForPatchPasswordReq.getPhoneNum()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation(value = "비밀번호 변경")
    @PatchMapping("/password")
    public BaseResponse<PatchPasswordRes> changePassword(@RequestBody PatchPasswordReq patchPasswordReq) {
        try{
            if (patchPasswordReq.getPassword().isEmpty()) {
                throw new BaseException(EMPTY_PASSWORD);
            }
            Long userId = (long) jwtService.getUserId();
            if(!userId.equals(patchPasswordReq.getUserId())){
                throw new BaseException(INVALID_PHONENUM);
            }
            PatchPasswordRes patchPasswordRes = userService.changeUserPassword(patchPasswordReq.getPassword(), patchPasswordReq.getPhoneNum());

            Log log = new Log(true, "User", "Update", "비밀번호 변경", patchPasswordReq.getUserId());
            logRepository.save(log);
            return new BaseResponse<>(patchPasswordRes);
        } catch (BaseException e) {
            Log log = new Log(false, "User", "Update", "비밀번호 변경", patchPasswordReq.getUserId());
            logRepository.save(log);
            return new BaseResponse<>(e.getStatus());
        }
    }


}
