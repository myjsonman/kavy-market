package com.elegant.elegantmarket.security.entity.auth;

import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
*  user
* @author 大狼狗 2019-06-26
*/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Integer id;

    private  Role role;
    /**
    * username
    */
    @NotBlank(message = "用户名不能为空")
    @Size(min=5, max=20,message = "用户名不能小于5位，大于20位")
    private String username;

    /**
    * password
    */
    @NotBlank(message = "密码不能为空")
    @Size(min=6,max = 20,message = "密码不能小于6位,大于20位")
    private String password;

    /**
    * user_img
    */
    private String userImg;

    /**
    * updatetime
    */
    private Date updatetime;

    /**
    * status
    */
    private String status;

    public User() {
    }

}