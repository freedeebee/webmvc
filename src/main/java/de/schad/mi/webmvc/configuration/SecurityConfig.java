package de.schad.mi.webmvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.schad.mi.webmvc.services.ObservationUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired ObservationUserDetailsService observationUserDetailsService;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb
            .inMemoryAuthentication()
            .withUser("admin")
            .password(getPasswordEncoder().encode("geheim"))
            .roles("ADMIN", "MEMBER");
        amb
            .userDetailsService(observationUserDetailsService)
            .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/users/create", "/h2-console/**").permitAll()
            .antMatchers("/users/**", "/h2-console/**").hasRole("ADMIN")
            .antMatchers("/login", "/", "/sichtung", "/css/**", "/images/**", "/fonts/**").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/sichtung")
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        .and()
            .csrf().ignoringAntMatchers("/h2-console/**")
        .and()
            .headers().frameOptions().sameOrigin();
    }

}