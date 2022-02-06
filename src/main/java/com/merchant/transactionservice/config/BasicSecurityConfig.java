package com.merchant.transactionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("MASTER_ADMIN", "SIMPLE_USER")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("SIMPLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //"admin" -> full access
        //"user" -> read only
        //no form login neither cross site enabled
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/transactions/**").hasRole("SIMPLE_USER")
                .antMatchers(HttpMethod.POST, "/api/v1/transactions").hasRole("MASTER_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/transactions/**").hasRole("MASTER_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/transactions/**").hasRole("MASTER_ADMIN")
                .antMatchers(HttpMethod.GET, "/actuator/**").hasRole("MASTER_ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}