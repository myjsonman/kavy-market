package com.elegant.elegantmarket.security.entity.auth;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  role
* @author 大狼狗 2019-06-26
*/
@Builder
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Long id;

    /**
    * rloe_name
    */
    private String rloeName;

    /**
    * updatetime
    */
    private Date updatetime;

    /**
    * role_desc
    */
    private String roleDesc;

  /*  public Role() {
    }*/

}