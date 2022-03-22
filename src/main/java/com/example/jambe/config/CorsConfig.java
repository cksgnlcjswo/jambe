package com.example.jambe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // js에서 json 처리 가능하도록
        config.addAllowedOrigin("*"); //모든 ip 응답허용
        config.addAllowedHeader("*"); //모든 헤더 응답 허용
        config.addAllowedMethod("*"); //모든 메소드 응답 허용
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
