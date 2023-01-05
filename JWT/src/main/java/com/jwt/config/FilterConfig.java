package com.jwt.config;


import com.jwt.filter.MyFilter1;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> filter1(){   //시큐리티 필터가 우선순위  - >  그 다음 이 필터가 실행
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns(("/*"));
        bean.setOrder(0); // 낮은 번호가 우선순위
        return bean;
    }
}
