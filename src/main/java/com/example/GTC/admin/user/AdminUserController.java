package com.example.GTC.admin.user;

import com.example.GTC.admin.user.AdminUserService;
import com.example.GTC.admin.user.model.GetUserRes;
import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    //모든 유저 검색
    @ApiOperation(value = "관리자 : 모든 유저 조회")
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> findAllUser() {
        return new BaseResponse<>(adminUserService.getAllUser());
    }

    //username 으로 검색
    @ApiOperation(value = "관리자 : 사용자 이름으로 유저 조회 , 사용자 이름은 중복이 안되므로 한명의 유저만 반환")
    @ApiImplicitParam(name = "username", required = true, dataType = "String", paramType = "query")
    @GetMapping("/username")
    public BaseResponse<GetUserRes> findAllUserByUserName(@RequestParam String username) {
        try {
            GetUserRes result = adminUserService.findUserByUserName(username);
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //name 으로 검색
    @ApiOperation(value = "관리자 : 이름으로 유저 조회, 이름은 중복 가능하므로 List 반환")
    @ApiImplicitParam(name = "name", required = true, dataType = "String", paramType = "query")
    @GetMapping("/name")
    public BaseResponse<List<GetUserRes>> findAllUserByName(@RequestParam String name) {
        try {
            return new BaseResponse<>(adminUserService.findUserByName(name));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //가입 날짜로 검색
    @ApiOperation(value = "관리자 : 가입 날짜로 유저 조회")
    @ApiImplicitParam(name = "date", required = true, dataType = "Date", paramType = "query")
    @GetMapping("/date")
    public BaseResponse<List<GetUserRes>> findAllUserByCreatedDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        try {
            return new BaseResponse<>(adminUserService.findUserByCreatedDate(date));
        } catch (BaseException e) {

            return new BaseResponse<>(e.getStatus());
        }

    }

    //활성화 상태로 검색
    @ApiOperation(value = "관리자 : 사용자 상태로 유저 조회")
    @ApiImplicitParam(name = "status", required = true, dataType = "String", paramType = "query")
    @GetMapping("/status")
    public BaseResponse<List<GetUserRes>> findAllUserByCreatedDate(@RequestParam String status) {
        try {
            return new BaseResponse<>(adminUserService.findUserByStatus(status));
        } catch (BaseException e) {

            return new BaseResponse<>(e.getStatus());
        }

    }
    //회원 정지 상태
    @ApiOperation(value = "관리자 : 특정 사용자를 강제 삭제")
    @ApiImplicitParam(name = "userId", required = true, dataType = "Long", paramType = "path",example  = "0")
    @PatchMapping("/stop/{userId}")
    public BaseResponse<String> inactiveUser(@PathVariable Long userId) {
        try {
            adminUserService.inactiveUser(userId);
            return new BaseResponse<>("해당 유저가 비활성화 되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
}
