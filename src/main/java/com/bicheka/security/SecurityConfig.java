package com.bicheka.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import com.bicheka.security.filter.AuthenticationFilter;
import com.bicheka.security.filter.JWTAuthorizationFilter;
import com.bicheka.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;


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
            .cors().and()
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/comment/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/confirm_email").permitAll()
                .requestMatchers(HttpMethod.GET, "/product/get_all_products").permitAll()
                // .requestMatchers(HttpMethod.POST, "/image/**").hasRole("STORE")
                .requestMatchers(HttpMethod.GET, SecurityConstants.GET_STORES).permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll() //permit all request at this path
                .requestMatchers(HttpMethod.DELETE, "/store").hasRole("STORE")
                .requestMatchers(HttpMethod.PUT, "/store").hasRole("STORE")
                .requestMatchers(HttpMethod.GET, "/store/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/product/get_product_by_id/**").permitAll()
                // .requestMatchers(HttpMethod.DELETE, "/store/**").hasRole("STORE")
                .anyRequest().authenticated()
            .and()  
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new com.bicheka.security.filter.ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authenticationFilter) //calls the authentication filter class that maps the reaquest into a user object
            .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);
            
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000"
            // ,"http://10.0.0.0/8"                                        
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}