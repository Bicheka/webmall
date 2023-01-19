package com.bicheka.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.bicheka.security.filter.AuthenticationFilter;
import com.bicheka.security.filter.JWTAuthorizationFilter;
import com.bicheka.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    // @Autowired
    // private UserDetailsService userDetailsService;

    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http        
    
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, SecurityConstants.GET_STORES).permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll() //permit all request at this path
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(new com.bicheka.security.filter.ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter) //calls the authentication filter class that maps the reaquest into a user object
            .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     return super.userDetailsService();
    // }

    
}