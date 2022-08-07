package com.example.GTC.admin.log;

import com.example.GTC.config.BaseException;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.log.LogService;
import com.example.GTC.log.model.Log;
import com.example.GTC.user.UserService;
import com.example.GTC.user.model.Role;
import com.example.GTC.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.GTC.config.BaseResponseStatus.NOT_ADMIN_LOGINED;

@RestController
@RequestMapping("/admin/logs")
public class AdminLogController {

    private final JwtService jwtService;
    private final LogService logService;
    private final UserService userService;

    @Autowired
    public AdminLogController(JwtService jwtService, LogService logService, UserService userService) {
        this.jwtService = jwtService;
        this.logService = logService;
        this.userService = userService;
    }

    @GetMapping("")
    public BaseResponse<List<Log>> findAllLogByModelName(@RequestParam String modelName, Pageable pageable) {
        try {
            if (!userService.findById((long) jwtService.getUserId()).get().getRole().equals(Role.ADMIN)) {
                throw new BaseException(NOT_ADMIN_LOGINED);
            }
            return new BaseResponse<>(logService.findAllLogByClass(modelName, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/class/date")
    public BaseResponse<List<Log>> filteredWithClassAndDateLogList(@RequestParam String modelName,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime startDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endDateTime,Pageable pageable) {

        try {
            if (!userService.findById((long) jwtService.getUserId()).get().getRole().equals(Role.ADMIN)) {
                throw new BaseException(NOT_ADMIN_LOGINED);
            }
            return new BaseResponse<>(logService.findAllLogByClassAndDateBetween(modelName, startDateTime, endDateTime, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/date")
    public BaseResponse<List<Log>> filteredWithClassAndDateLogList(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime startDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endDateTime,Pageable pageable) {
        try {
            if (!userService.findById((long) jwtService.getUserId()).get().getRole().equals(Role.ADMIN)) {
                throw new BaseException(NOT_ADMIN_LOGINED);
            }
            return new BaseResponse<>(logService.findAllByCreatedDateBetween(startDateTime, endDateTime, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
