package io.ppesky.auth.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.ppesky.auth.config.util.BearerTokenResolver;
import io.ppesky.auth.config.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BearerJwtTokenInterceptor implements HandlerInterceptor {

	@Autowired JwtTokenProvider jwtTokenProvider;
	@Autowired BearerTokenResolver bearerTokenResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    	log.debug(this.getClass().getSimpleName() + ".preHandle() : " + request.getRequestURI());
    	
		String token = bearerTokenResolver.resolve(request);
		Claims claims = jwtTokenProvider.getClaimsAccessToken(token);
		
		request.setAttribute("UserUid",   claims.get("user-uid"));
		
        return true;
    }
}
