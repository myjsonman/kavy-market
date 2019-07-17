package com.elegant.elegantmarket.security.service.serviceImp;

import com.elegant.elegantmarket.security.entity.auth.Role;
import com.elegant.elegantmarket.security.entity.auth.UserDetail;
import com.elegant.elegantmarket.security.mapper.RoleMapper;
import com.elegant.elegantmarket.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证方法
 *
 * @author hackyo
 * Created on 2017/12/8 9:18.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userRepository;
    @Autowired
    private RoleMapper roleMapper;
    /*@Autowired
    public JwtUserDetailsServiceImpl(UserMapper userRepository) {
        this.userRepository = userRepository;
    }*/

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetail userDetail = userRepository.findByUsername(name);
        if (userDetail == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
        }
        Role role = roleMapper.findRoleByUserId(userDetail.getId());
        userDetail.setRole(role);
        return userDetail;
    }

}