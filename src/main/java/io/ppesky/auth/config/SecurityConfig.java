package io.ppesky.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    .httpBasic().disable().logout().disable()
	    .csrf().disable()
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    ;
	    
	    return http.build();
	}
}
