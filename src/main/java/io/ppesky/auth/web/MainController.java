package io.ppesky.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ppesky.auth.config.util.JwtTokenProvider;
import io.ppesky.auth.web.entity.DefaultResponseEntity;

@RestController
public class MainController {
	
	@Autowired JwtTokenProvider jwtTokenProvider;

	@GetMapping("/")
	public DefaultResponseEntity<?> index() {
		return DefaultResponseEntity.builder().timestamp(System.currentTimeMillis()).build();
	}

	@GetMapping("/create")
	public DefaultResponseEntity<?> login() {
		//TODO Login process
		
		
		return DefaultResponseEntity.builder()
				.entity(jwtTokenProvider.createAccessToken("test@login.id"))
				.timestamp(System.currentTimeMillis()).build();
	}

	@GetMapping("/validation")
	public DefaultResponseEntity<?> validation() {
		return DefaultResponseEntity.builder()
				.entity(Boolean.TRUE)
				.timestamp(System.currentTimeMillis()).build();
	}

}
