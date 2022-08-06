package com.example.GTC.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthLoginModel extends AuthModel {
    private String jwt;
    private Long userId;
}
