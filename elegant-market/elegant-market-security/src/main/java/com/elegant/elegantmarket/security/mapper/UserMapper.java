package com.elegant.elegantmarket.security.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elegant.elegantmarket.security.entity.auth.User;
import com.elegant.elegantmarket.security.entity.auth.UserDetail;
import org.mapstruct.Mapper;

public interface UserMapper  extends BaseMapper<User>{

    UserDetail findByUsername(String username);

    /**
     * 创建新用户
     * @param userDetail
     */
    void insert(UserDetail userDetail);


}
