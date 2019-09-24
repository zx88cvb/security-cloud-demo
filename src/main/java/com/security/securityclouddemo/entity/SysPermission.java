package com.security.securityclouddemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class SysPermission implements Serializable {
    @Id
    private Integer id;
    /**
     * 资源编码
     */
    private String perCode;
    /**
     * 资源URL
     */
    private String perPath;
}
