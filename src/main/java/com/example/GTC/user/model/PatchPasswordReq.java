package com.example.GTC.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchPasswordReq {
    private Long userId;
    private String phoneNum;
    private String password;
}
