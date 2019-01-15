package com.ccssoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  SsoAuthenticationConfig ssoAuthenticationConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers()
        .anyRequest()
        .and()
        .authorizeRequests()
        .antMatchers("/oauth/token/*", "/login", "/user", "/info")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/index")
        .and()
        .apply(ssoAuthenticationConfig);

    http.exceptionHandling()
        .defaultAuthenticationEntryPointFor(
            new Http403ForbiddenEntryPoint(), new AntPathRequestMatcher("/login"));
  }
}
