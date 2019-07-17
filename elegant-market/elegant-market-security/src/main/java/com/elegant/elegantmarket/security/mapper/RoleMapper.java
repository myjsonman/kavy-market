package com.elegant.elegantmarket.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elegant.elegantmarket.security.entity.auth.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 创建用户角色
     * @param userId
     * @param roleId
     * @return
     */
    int insertRole( long userId,  long roleId);

    /**
     * 根据角色id查找角色
     * @param roleId
     * @return
     */
    Role findRoleById(long roleId);

    /**
     * 根据用户id查找该用户角色
     * @param userId
     * @return
     */
    Role findRoleByUserId( long userId);
}
