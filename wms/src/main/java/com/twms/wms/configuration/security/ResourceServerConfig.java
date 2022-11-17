package com.twms.wms.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;

    @Autowired
    private Environment env;

    private static final String[] PUBLIC = {"/oauth/token", "/user/signup"};
    private static final String[] ROLE_CLIENT = {"/**"};
    private static final String[] OPERATOR = {"/**"};
    private static final String[] BRANCH_MANAGER = {"/**"};
    private static final String[] MANAGER = {"/**"};
    private static final String[] ADMIN = {"/**"};

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(Arrays.asList(env.getActiveProfiles()).contains("test")){
            http
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll();
        } else if(Arrays.asList(env.getActiveProfiles()).contains("dev")){
            http
                    .authorizeRequests()
                    .antMatchers(PUBLIC).permitAll()
                    .antMatchers(ADMIN).hasRole("ADMIN")
                    .anyRequest().authenticated();
        }

    }

}
