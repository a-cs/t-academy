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


    private static final String[] PUBLIC = {"/oauth/token"};

    private static final String[] ROLE_CLIENT_GET = {"/branch", "/warehouseSlot/client/**"};
    private static final String[] ROLE_CLIENT_POST = {"/warehouseSlot/client/**"};
    private static final String[] OPERATOR = {"/sku/**", "/measurement-unit/**", "/category/**"};
    private static final String[] MANAGER = {"/sku/**", "/measurement-unit/**", "/category/**", "/client/**", "/user/**"};
    private static final String[] MANAGER_GET = {"/branch/**"};
    private static final String[] ADMIN = {"/**"};

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http
                    .cors().and()
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll();
        } else if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            http
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers("/user/setpassword", "/user/resetpassword").permitAll()
                    .antMatchers(HttpMethod.GET, "/branch").hasAnyRole("CLIENT", "MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/warehouseSlot/client/filtered").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/warehouseSlot/client/**").hasAnyRole("CLIENT", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/sku/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/sku/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/measurement-unit/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/measurement-unit/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/category/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/category/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/client").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/client/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/user/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/roles/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/transaction/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/transaction/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/transaction/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                    .anyRequest().hasRole("ADMIN");
        }else if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            http
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers("/user/setpassword", "/user/resetpassword").permitAll()
                    .antMatchers(HttpMethod.GET, "/branch").hasAnyRole("CLIENT", "MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/warehouseSlot/client/filtered").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/warehouseSlot/client/**").hasAnyRole("CLIENT", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/sku/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/sku/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/measurement-unit/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/measurement-unit/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/category/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/category/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/client").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/client/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/user/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/roles/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.GET, "/transaction/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers(HttpMethod.POST, "/transaction/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                    .antMatchers("/transaction/**").hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                    .anyRequest().hasRole("ADMIN");
        }
    }

}
