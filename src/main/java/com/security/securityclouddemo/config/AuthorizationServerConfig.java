package com.security.securityclouddemo.config;

import com.security.securityclouddemo.handler.CustomAccessDeniedHandler;
import com.security.securityclouddemo.handler.CustomAuthEntryPoint;
import com.security.securityclouddemo.handler.CustomWebResponseExceptionTranslator;
import com.security.securityclouddemo.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManagerBean;

    private final RedisConnectionFactory redisConnectionFactory;

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthEntryPoint customAuthEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Resource
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .authenticationEntryPoint(customAuthEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
        log.info("AuthorizationServerSecurityConfigurer is complete");
    }

    /**
     * 配置客户端详情信息(Client Details)
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     *
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
        log.info("ClientDetailsServiceConfigurer is complete!");
    }

    /**
     * 配置授权、令牌的访问端点和令牌服务
     * tokenStore：采用redis储存
     * authenticationManager:身份认证管理器, 用于"password"授权模式
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManagerBean)
                .userDetailsService(customUserDetailsService)
                .tokenServices(tokenServices())
                .exceptionTranslator(customWebResponseExceptionTranslator);

        log.info("AuthorizationServerEndpointsConfigurer is complete.");
    }

    /**
     * redis存储方式
     *
     * @return
     */
    @Bean("redisTokenStore")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 采用RSA加密生成jwt
     *
     * @return
     */
    /*@Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("hq-jwt.jks"), "hq940313".toCharArray());
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("hq-jwt"));
        return jwtAccessTokenConverter;
    }*/

    /**
     * 配置生成token的有效期以及存储方式（此处用的redis）
     *
     * @return
     */
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(redisTokenStore());
//        defaultTokenServices.setTokenEnhancer(jwtTokenEnhancer());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(30));
        defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
        return defaultTokenServices;
    }

    /**
     * 客户端信息配置在数据库
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }
}
