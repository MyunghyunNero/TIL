package com.jwt.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;


        //토큰 : cos 이걸 만들어 줘야함 .Id,pw 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
        // 요청 할떄 마다  header에 Authorization 에 value으로 토큰을 가지고 온다
        //그떄 토큰이 넘어오면 이 토큰이 내가 만든이 토큰이 맞는지만 검증하면 됨(RSA,HS256)
        if(req.getMethod().equals("POST")){
            String headerAuth = req.getHeader("Authoriztion");
            System.out.println(headerAuth);

            if(headerAuth.equals("cos")) {
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                PrintWriter outPrintWriter = res.getWriter();
                outPrintWriter.println("인증 안됨");
            }
        }

        System.out.println("필터");

    }
}
