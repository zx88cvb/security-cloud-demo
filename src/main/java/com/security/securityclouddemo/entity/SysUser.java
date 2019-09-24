package com.security.securityclouddemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private Integer[] roles;

}
