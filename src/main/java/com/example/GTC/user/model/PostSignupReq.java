package com.example.GTC.user.model;

import com.example.GTC.auth.model.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSignupReq {
    private String email;
    private String password;
    private AuthProvider authProvider;
    private String name;
    private String phoneNum;
    private String userName;
    private String birth;
    private String imgUrl;
}
