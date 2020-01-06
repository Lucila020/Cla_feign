package com.example.test.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.mvc.servlet.path:/}")
    private String path;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests().antMatchers(path + "/*").permitAll();
    }

}
