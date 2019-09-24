package com.security.securityclouddemo.config;

import com.security.securityclouddemo.handler.CustomAccessDeniedHandler;
import com.security.securityclouddemo.handler.CustomAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器
 * 资源服务器拦截除排除自定义删除token的地址，以及替换成自定义的错误返回。
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private CustomAuthEntryPoint customAuthEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.authenticationEntryPoint(customAuthEntryPoint).accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(customAuthEntryPoint)
                .and().authorizeRequests()
                .antMatchers("/oauth/remove_token").permitAll()
                .anyRequest().authenticated();
        ;
    }
}
