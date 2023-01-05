package com.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config =  new CorsConfiguration();
        config.setAllowCredentials(true);  // 내 서버가 응답을 할 떄 json을 자바스크립트에서 처리 할 수 있게 설정하는 것
        config.addAllowedOrigin("*");  //모든 ip에 응답을 허용 하겠다.
        config.addAllowedHeader("*");  // 모든 header에 응답을 허용
        config.addAllowedMethod("*");  // 모든 메서드 요청 허용
        source.registerCorsConfiguration("/api/**",config);
        return new CorsFilter(source);
    }
}
