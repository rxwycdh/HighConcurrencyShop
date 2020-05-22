package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @FileName CorsConfig
 * @Description
 * @Author jiuhao
 * @Date 2020/5/16 19:38
 * @Modified
 * @Version 1.0
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {

    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许跨域请求的域名地址，如果设为*，则是由所有网址发来的都可以访问我们的接口（不推荐）
        config.addAllowedOrigin("http://127.0.0.1:8080");
        // 设置是否发送cookie信息
        config.setAllowCredentials(true);
        // 设置允许请求的http方式
        config.addAllowedMethod("*");
        // 设置允许的header
        config.addAllowedHeader("*");
        config.setMaxAge(3600L);
        // 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSouce = new UrlBasedCorsConfigurationSource();
        corsSouce.registerCorsConfiguration("/**", config);
        // 返回自定义的corsSouce
        return new CorsFilter(corsSouce);
    }

}
