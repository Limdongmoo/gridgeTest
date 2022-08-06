package com.example.GTC.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthSignUpModel extends AuthModel{
    private AuthProvider authProvider;
    private String email;
}
