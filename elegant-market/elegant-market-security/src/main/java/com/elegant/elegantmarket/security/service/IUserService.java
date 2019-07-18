package com.elegant.elegantmarket.security.service;


import com.elegant.elegantmarket.common.result.ResultJson;
import com.elegant.elegantmarket.security.entity.ResponseUserToken;
import com.elegant.elegantmarket.security.entity.auth.User;
import com.elegant.elegantmarket.security.entity.auth.UserDetail;


/**
 * 用户操作接口
 *
 * @author hackyo
 * Created on 2017/12/3 11:53.
 */
public interface IUserService {

    /**
     * 注册用户
     * @return
     */
    void register(UserDetail userDetail);


    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

    /**
     * 刷新Token
     * @param oldToken
     * @return
     */
    ResponseUserToken refresh(String oldToken);

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    UserDetail getUserByToken(String token);
}