package com.mindhub.homebancking.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                /*
                .antMatchers("/manager.html", "/manager.js").hasAuthority("ADMIN")
                .antMatchers("/rest/**", "/h2-console/**", "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/**", "/api/loans").hasAnyAuthority("ADMIN", "CLIENT")
                */

                .antMatchers("/web/account.html", "/web/accounts.html", "/web/cards.html","/web/create-cards.html","/web/transfers.html","/web/loan-application.html").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/loans","/api/transactions").hasAuthority("CLIENT")
                .antMatchers("/rest/","/api/clients").hasAuthority("ADMIN")
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/web/style.css").permitAll()
                .antMatchers("/web/js/").permitAll();

                /*
                .antMatchers("/web/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/web/account.html").hasAuthority("CLIENT")
                .antMatchers("/web/cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/create-cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/transfers.html").hasAuthority("CLIENT")
                .antMatchers("/web/loan-application.html").hasAuthority("CLIENT")
                */

               // .antMatchers("/web/js/**", "/web/style.css", "/web/img", "/web/index.html", "/web/login").permitAll()

               // .antMatchers("/**").permitAll();


        http.formLogin()

                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");



        http.logout().logoutUrl("/api/logout");


        // turn off checking for CSRF tokens

        http.csrf().disable();



        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }

}