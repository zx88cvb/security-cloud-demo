package com.security.securityclouddemo.controller;

import com.security.securityclouddemo.common.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("hello")
    @PreAuthorize("hasAuthority('add_user')")
    public Result hello() {
        return Result.ok("hello");
    }
}
