package com.jwt.config;


import com.jwt.filter.MyFilter1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();  //시큐리티 필터가 기존 필터보다 우선순위
        // 내가 만든 필터를 더 우선순위를 두고 싶다면 http.addFilterBefore() 함수 이용
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)  // @CrossOrigin(인증 X) , 시큐리티 필터에 등록 인증(O)
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/**")
                .hasAnyRole("ADMIN","USER","MANAGER")
                .requestMatchers("/api/v1/manager/**")
                .hasAnyRole("ADMIN","MANAGER")
                .requestMatchers("/api/v1/admin/**")
                .hasRole("ADMIN")
                .anyRequest().permitAll();



        return http.build();
    }
}
