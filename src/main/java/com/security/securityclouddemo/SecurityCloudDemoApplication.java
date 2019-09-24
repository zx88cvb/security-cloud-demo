package com.security.securityclouddemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.security.securityclouddemo.mapper")
public class SecurityCloudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCloudDemoApplication.class, args);
    }

}
