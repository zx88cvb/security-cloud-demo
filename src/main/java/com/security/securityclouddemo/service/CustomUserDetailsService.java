package com.security.securityclouddemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.security.securityclouddemo.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService, IService<SysUser> {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
