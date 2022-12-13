package com.twms.wms.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Autowired
    private Environment env;
    @Value("${PROD_URL:Unknown}")
    private String uri;
    @Bean
    public FilterRegistrationBean customCorsFilter() {
        String url = "";
        if (Arrays.asList(env.getActiveProfiles()).contains("test")){
            url = "http://localhost:4200";
        }else if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            url = "http://localhost:4200";
        }else if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            url = uri;
        }
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(url);
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}