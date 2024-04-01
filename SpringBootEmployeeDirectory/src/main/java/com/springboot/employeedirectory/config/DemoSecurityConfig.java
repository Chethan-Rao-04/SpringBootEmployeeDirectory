package com.springboot.employeedirectory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add our users for in memory authentication
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
            .withUser("Rahul").password(passwordEncoder.encode("pass123")).roles("EMPLOYEE")
            .and()
            .withUser("Matt").password(passwordEncoder.encode("pass123")).roles("EMPLOYEE", "MANAGER")
            .and()
            .withUser("Siri").password(passwordEncoder.encode("pass123")).roles("EMPLOYEE", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
            .antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
            .antMatchers("/employees/delete").hasRole("ADMIN")
            .antMatchers("/employees/**").hasRole("EMPLOYEE")
            .antMatchers("/resources/**").permitAll()
            .and()
            .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
