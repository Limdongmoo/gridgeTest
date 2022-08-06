package com.example.GTC.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchPasswordRes {
    private Long userId;
    private String message;

}
