package com.ibm.ph.edm.security;

import com.ibm.ph.edm.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

//http://www.codesandnotes.be/2014/10/31/restful-authentication-using-spring-security-on-spring-boot-and-jquery-as-a-web-client/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RESTAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RESTAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private RESTLogoutSuccessHandler restLogoutSuccessHandler;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/**").authenticated()
                //.and().formLogin().loginProcessingUrl("/auth/login") //@TODO: Make this work!!!!
                .and().logout().logoutSuccessHandler(restLogoutSuccessHandler)
                .and().addFilter(getSuccessFilter());

        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
        //http.logout().logoutSuccessHandler(restLogoutSuccessHandler);
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
    }

    /**
     * Filter to override the authentication with json payload
     * @return
     * @throws Exception
     */
    protected CustomUsernamePasswordAuthenticationFilter getSuccessFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter();

        authenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);

        return authenticationFilter;
    }
}