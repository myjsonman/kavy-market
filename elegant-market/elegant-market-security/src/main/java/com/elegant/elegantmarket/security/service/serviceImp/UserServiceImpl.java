package com.elegant.elegantmarket.security.service.serviceImp;

import com.elegant.elegantmarket.common.exception.CustomException;
import com.elegant.elegantmarket.common.result.ResultCode;
import com.elegant.elegantmarket.common.result.ResultJson;
import com.elegant.elegantmarket.security.entity.ResponseUserToken;
import com.elegant.elegantmarket.security.entity.auth.Role;
import com.elegant.elegantmarket.security.entity.auth.User;
import com.elegant.elegantmarket.security.entity.auth.UserDetail;
import com.elegant.elegantmarket.security.mapper.RoleMapper;
import com.elegant.elegantmarket.security.mapper.UserMapper;
import com.elegant.elegantmarket.security.service.IUserService;
import com.elegant.elegantmarket.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 用户操作接口实现
 *
 * @author hackyo
 * Created on 2017/12/3 11:53.
 */
@Service
public class UserServiceImpl implements IUserService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    //private JwtTokenUtil jwtTokenUtil;
    private JwtUtils jwtTokenUtil;
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;


    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtTokenUtil, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
    }

 /*      @Override
    public UserDetail register(UserDetail userDetail) {
        final String username = userDetail.getUsername();
        if(authMapper.findByUsername(username)!=null) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "用户已存在"));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userDetail.getPassword();
        userDetail.setPassword(encoder.encode(rawPassword));
        authMapper.insert(userDetail);
        long roleId = userDetail.getRole().getId();
        Role role = authMapper.findRoleById(roleId);
        userDetail.setRole(role);
        authMapper.insertRole(userDetail.getId(), roleId);
        return userDetail;
    }*/

    @Override
    public ResultJson register(UserDetail  userDetail) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String username = userDetail.getUsername();
        if (userMapper.findByUsername(username) != null) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "用户已存在"));
        }
        //获取密码
        final  String rawPassword = userDetail.getPassword();
        // 密码加密
        userDetail.setPassword(encoder.encode(rawPassword));
        //保存
        userMapper.insert(userDetail);
        long roleId = userDetail.getRole().getId();
        Role role = roleMapper.findRoleById(roleId);
        userDetail.setRole(role);
        roleMapper.insertRole(userDetail.getId(),roleId);
        return ResultJson.success();
    }

    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateAccessToken(userDetail);
        //存储token
        jwtTokenUtil.putToken(username, token);
        return new ResponseUserToken(token, userDetail);

    }

    @Override
    public void logout(String token) {
        token = token.substring(tokenHead.length());
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(userName);
    }

    @Override
    public ResponseUserToken refresh(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetail userDetail = (UserDetail) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token)){
            token =  jwtTokenUtil.refreshToken(token);
            return new ResponseUserToken(token, userDetail);
        }
        return null;
    }

    @Override
    public UserDetail getUserByToken(String token) {
        token = token.substring(tokenHead.length());
        return jwtTokenUtil.getUserFromToken(token);
    }

    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new CustomException(ResultJson.failure(ResultCode.LOGIN_ERROR, e.getMessage()));
        }
    }

}