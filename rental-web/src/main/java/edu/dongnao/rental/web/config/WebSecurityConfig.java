package edu.dongnao.rental.web.config;

import edu.dongnao.rental.web.security.AuthFilter;
import edu.dongnao.rental.web.security.LoginAuthFailHandler;
import edu.dongnao.rental.web.security.LoginSuccessHandler;
import edu.dongnao.rental.web.security.LoginUrlEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Traveler
 * @date 2019/12/20 15:09
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

        // 资源访问权限
        http.authorizeRequests()
                .antMatchers("/static/**", "/admin/login", "/user/login").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**", "/api/user/**").hasAnyRole("ADMIN", "USER")
                .and()
                .exceptionHandling().authenticationEntryPoint(urlEntryPoint())
                .accessDeniedPage("/403")
                ;
        // 关闭CSRF
        http.csrf().disable();
        // 允许使用frame访问同源域名中的页面
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() {
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = super.authenticationManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationManager;
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginAuthFailHandler authFailHandler() {
        return new LoginAuthFailHandler(urlEntryPoint());
    }

    @Bean
    public LoginUrlEntryPoint urlEntryPoint() {
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public AuthFilter authFilter() {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setAuthenticationFailureHandler(authFailHandler());
        System.out.println("web security start....");
        return authFilter;
    }
}
