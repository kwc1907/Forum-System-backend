package com.example.community;

import com.example.community.jwt.JwtAuthenticationFilter;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@MapperScan("com.example.community.mapper")
@SpringBootApplication
public class CommunityApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CommunityApplication.class);
    }
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
