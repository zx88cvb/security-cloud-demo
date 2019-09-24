package com.security.securityclouddemo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.securityclouddemo.entity.CustomUser;
import com.security.securityclouddemo.entity.SysPermission;
import com.security.securityclouddemo.entity.SysRole;
import com.security.securityclouddemo.entity.SysUser;
import com.security.securityclouddemo.mapper.SysPermissionMapper;
import com.security.securityclouddemo.mapper.SysRoleMapper;
import com.security.securityclouddemo.mapper.SysUserMapper;
import com.security.securityclouddemo.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements CustomUserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username));

        Set<String> permissions = new HashSet<>();

        //设置角色列表  （ID）
        Set<Integer> roleIds = sysRoleMapper.listRolesByUserId(sysUser.getId())
                .stream()
                .map(r -> {
                    permissions.add("ROLE_" + r.getId());
                    return r.getId();
                })
                .collect(Collectors.toSet());

        //设置权限列表（menu.permission）
        roleIds.forEach(roleId -> {
            List<String> permissionList = sysPermissionMapper.listPremissionByRoleId(roleId)
                    .stream()
                    .filter(sysPermission -> StringUtils.isNotEmpty(sysPermission.getPerCode()))
                    .map(SysPermission::getPerCode)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });

        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));

        return new CustomUser(sysUser.getId(), sysUser.getUsername(),
                sysUser.getPassword(), true, true, true,
                true, authorities);
    }


}
