package com.jwt.config;


import com.jwt.filter.MyFilter1;
import com.jwt.jwt.JwtAuthenticationFilter;
import com.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CorsConfig corsConfig;

//    @Bean
//    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();  //시큐리티 필터가 기존 필터보다 우선순위
//        // 내가 만든 필터를 더 우선순위를 두고 싶다면 http.addFilterBefore() 함수 이용
//        http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class);
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(corsFilter)  // @CrossOrigin(인증 X) , 시큐리티 필터에 등록 인증(O)
//                .formLogin().disable() //UsernamePasswordAuthenticationFilter 동작 안함 따로 만들어줘서 id,pw 인증
//                .addFilter(new JwtAuthenticationFilter(authenticationManager)) // 요걸로 등록
//                .httpBasic().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/user/**")
//                .hasAnyRole("ADMIN","USER","MANAGER")
//                .requestMatchers("/api/v1/manager/**")
//                .hasAnyRole("ADMIN","MANAGER")
//                .requestMatchers("/api/v1/admin/**")
//                .hasRole("ADMIN")
//                .anyRequest().permitAll();
//        return http.build();
//    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl()) // 커스텀 필터 등록
                .and()
                .authorizeRequests(authroize -> authroize.requestMatchers("/api/v1/user/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                        .requestMatchers("/api/v1/manager/**")
                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                        .requestMatchers("/api/v1/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll())
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
        }
    }
}
