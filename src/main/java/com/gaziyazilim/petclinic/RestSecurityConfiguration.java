package com.gaziyazilim.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@Order(value = 1)
public class RestSecurityConfiguration extends AbstractSecurityConfiguration {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')");
        http.csrf().disable();
        http.httpBasic();
    }
}