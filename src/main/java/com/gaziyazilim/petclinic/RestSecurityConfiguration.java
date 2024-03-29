package com.gaziyazilim.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(value=1)
@Configuration
public class RestSecurityConfiguration extends AbstractSecurityConfiguration {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/rest/**").authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')");
        http.csrf().disable();
        http.httpBasic();
    }
}