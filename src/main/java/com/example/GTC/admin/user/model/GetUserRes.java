package com.example.GTC.admin.user.model;

import com.example.GTC.user.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetUserRes {
    private Long userId;
    private String userName;
    private String phoneNum;
    private LocalDateTime createdDate;
    private String status;

    @Builder
    public GetUserRes(Long userId, String userName, String phoneNum, LocalDateTime createdDate,String status) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.createdDate = createdDate;
        this.status = status;
    }

    public static GetUserRes from(User user) {
        return GetUserRes.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .phoneNum(user.getPhoneNum())
                .createdDate(user.getCreatedDate())
                .status(user.getStatus())
                .build();
    }
}
