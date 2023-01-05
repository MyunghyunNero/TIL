package com.jwt.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@RequiredArgsConstructor
//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username,password 전송하면 (post)
// UsernamePasswordAuthenticaitonFilter 동작
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    // /login 요청이 오면 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //username , password 받아서
        // 정상인지 로그인 시도 authenticationManager로 로그인 시도하면
        //principalDetailsService 호출 loadUserByUsername() 함수 실행됨

        //PrincipalDetails 세션에 담고
        // jwt 토큰에 담아서 응답
        return super.attemptAuthentication(request, response);}
}
