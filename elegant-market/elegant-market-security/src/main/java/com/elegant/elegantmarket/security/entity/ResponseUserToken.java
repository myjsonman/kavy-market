package com.elegant.elegantmarket.security.entity;

import com.elegant.elegantmarket.security.entity.auth.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserToken {
    private String token;
    private UserDetail userDetail;
}