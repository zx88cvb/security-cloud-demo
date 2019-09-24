package com.security.securityclouddemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.securityclouddemo.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return
     */
    List<SysPermission> listPremissionByRoleId(Integer roleId);
}
