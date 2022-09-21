package io.ppesky.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -184458263805225159L;

	public CustomAuthenticationException(String msg) {
		super(msg);
	}

	public CustomAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
