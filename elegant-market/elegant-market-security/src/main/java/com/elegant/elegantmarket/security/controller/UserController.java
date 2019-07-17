package com.elegant.elegantmarket.security.controller;

import com.elegant.elegantmarket.common.result.ResultCode;
import com.elegant.elegantmarket.common.result.ResultJson;
import com.elegant.elegantmarket.security.entity.ResponseUserToken;
import com.elegant.elegantmarket.security.entity.auth.Role;
import com.elegant.elegantmarket.security.entity.auth.User;
import com.elegant.elegantmarket.security.entity.auth.UserDetail;
import com.elegant.elegantmarket.security.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class UserController {


    @Value("${jwt.header}")
    private String tokenHeader;


    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     *
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "登陆", notes = "登陆成功返回token,测试管理员账号:admin,123456;用户账号：les123,admin")
    public ResultJson<ResponseUserToken> login(
            @Valid @RequestBody User user){
        final ResponseUserToken response = userService.login(user.getUsername(), user.getPassword());
        return ResultJson.ok(response);
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "根据token获取用户信息", notes = "根据token获取用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public ResultJson getUser(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        UserDetail userDetail = userService.getUserByToken(token);
        return ResultJson.ok(userDetail);
    }
    /**
     * 用户注册
     *
     * @param user          用户信息
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ResultJson register(@RequestBody User user)  {

        if (StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
       UserDetail userDetail = new UserDetail(user.getUsername(), user.getPassword(), Role.builder().id(1l).build());
        return ResultJson.ok(userService.register(userDetail));
    }

    @GetMapping(value = "refresh")
    @ApiOperation(value = "刷新token")
    public ResultJson refreshAndGetAuthenticationToken(
            HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        ResponseUserToken response = userService.refresh(token);
        if(response == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "token无效");
        } else {
            return ResultJson.ok(response);
        }
    }

}
