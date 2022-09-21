package io.ppesky.auth.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.ppesky.auth.config.util.BearerTokenResolver;
import io.ppesky.auth.config.util.JwtTokenProvider;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	
//        registry.addMapping("/**")
//			.allowedOrigins("*")
//			.allowedMethods("*")
//			.maxAge(1000 * 24 * 60);
//    }
	
	@Autowired BearerJwtTokenInterceptor bearerJwtTokenInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(bearerJwtTokenInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/favicon.ico","/error","/","/login","/create");
	}

//	@Autowired JwtTokenProvider jwtTokenProvider;
//	@Autowired BearerTokenResolver bearerTokenResolver;
//
//    @Bean
//    public FilterRegistrationBean<Filter> logFilter() {
//        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
//        filterFilterRegistrationBean.setFilter(new BearerJwtAuthenticationFilter(bearerTokenResolver, jwtTokenProvider));
//        filterFilterRegistrationBean.setOrder(1);
//        filterFilterRegistrationBean.addUrlPatterns("/*");
//        return filterFilterRegistrationBean;
//
//    }
}
