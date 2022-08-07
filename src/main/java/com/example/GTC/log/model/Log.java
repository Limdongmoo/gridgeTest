package com.example.GTC.log.model;


import com.example.GTC.feed.model.BaseTime;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@NoArgsConstructor
@Getter
public class Log extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    private Boolean isSuccess;
    private String object;
    private String method;
    private String message;
    private Long userId;

    public Log(Boolean isSuccess, String object, String method, String message, Long userId) {
        this.isSuccess = isSuccess;
        this.object = object;
        this.method = method;
        this.message = message;
        this.userId = userId;
    }
}
