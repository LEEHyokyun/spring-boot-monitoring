package com.spring.boot.monitoring.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserCreateRequest {
    private String loginId;
    private String email;
    private String password;
    private String name;
}
