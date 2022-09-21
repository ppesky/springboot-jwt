package io.ppesky.auth.config.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.ppesky.auth.exception.CustomAuthenticationException;


/**
 * spring-boot-starter-oauth2-resource-server 의 
 * {@link org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver} 에서 일부 발췌.
 */
@Component
public class BearerTokenResolver {

	private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);

	private boolean allowFormEncodedBodyParameter = false;

	private boolean allowUriQueryParameter = false;

	private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

	public String resolve(final HttpServletRequest request) {
		final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
		final String parameterToken = isParameterTokenSupportedForRequest(request)
				? resolveFromRequestParameters(request) : null;
		if (authorizationHeaderToken != null) {
			if (parameterToken != null) {
				throw new CustomAuthenticationException("Found multiple bearer tokens in the request");
			}
			return authorizationHeaderToken;
		}
		if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
			return parameterToken;
		}
		return null;
	}

	private String resolveFromAuthorizationHeader(HttpServletRequest request) {
		String authorization = request.getHeader(this.bearerTokenHeaderName);
		if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
			return null;
		}
		Matcher matcher = authorizationPattern.matcher(authorization);
		if (!matcher.matches()) {
			throw new CustomAuthenticationException("Bearer token is malformed");
		}
		return matcher.group("token");
	}

	private static String resolveFromRequestParameters(HttpServletRequest request) {
		String[] values = request.getParameterValues("access_token");
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length == 1) {
			return values[0];
		}
		throw new CustomAuthenticationException("Found multiple bearer tokens in the request");
	}

	private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
		return (("POST".equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| "GET".equals(request.getMethod()));
	}

	private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
		return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
	}

}
