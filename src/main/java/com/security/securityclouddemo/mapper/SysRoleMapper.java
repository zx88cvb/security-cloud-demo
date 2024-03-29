package com.security.securityclouddemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.securityclouddemo.entity.SysPermission;
import com.security.securityclouddemo.entity.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(Integer userId);
}
