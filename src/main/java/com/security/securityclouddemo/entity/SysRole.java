package com.security.securityclouddemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
public class SysRole implements Serializable {
    @Id
    private Integer id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色code
     */
    private String roleCode;

    /**
     * 资源合集
     */
    private List<SysPermission> permissions;
}
