package com.wix.server.security;

import com.wix.server.manager.UserManager;
import com.wix.server.manager.WixUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.logging.Logger;

/**
 * Created by racastur on 27-02-2015.
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = Logger.getLogger(SecurityConfig.class.getName());

    @Autowired
    private WixUserDetailsService userDetailsService;

    @Autowired
    private UserManager userManager;

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(userDetailsService.getPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.userDetailsService(userDetailsService());

        http.csrf().disable().authorizeRequests()
                .antMatchers("/_ah/**").permitAll()
                .antMatchers("/services/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll().logoutSuccessUrl("/login")
                .and()
                .httpBasic();

    }

}