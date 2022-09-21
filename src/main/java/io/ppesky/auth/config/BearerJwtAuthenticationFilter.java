package io.ppesky.auth.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.ppesky.auth.config.util.BearerTokenResolver;
import io.ppesky.auth.config.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BearerJwtAuthenticationFilter extends OncePerRequestFilter {

	private final BearerTokenResolver bearerTokenResolver;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.debug(this.getFilterName() + " : " + request.getRequestURI());
		
		try {
			String token = bearerTokenResolver.resolve(request);
			Claims claims = jwtTokenProvider.getClaimsAccessToken(token);
			
			request.setAttribute("UserUid",   claims.get("user-uid"));
//			request.setAttribute("UserRoles", claims.get("user-roles"));
			
			filterChain.doFilter(request, response);
			
//			LocalDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			
		} catch(ResponseStatusException e) {
			response.setStatus(e.getRawStatusCode());
	        response.setContentType("application/json");
	        response.getWriter().write(
	        		"{\"timestamp\": "+System.currentTimeMillis()+", "
	        				+ "\"message\":\""+e.getReason()+"\"}");
		}
	}
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
//		log.debug(this.getFilterName() + " : " + request.getRequestURI());
		
		final String[] values = {"/favicon.ico","/error","/","/login","/create"};
		String requestUri = request.getRequestURI();
        return Arrays.stream(values).anyMatch(requestUri::equals);
	}
	
}
