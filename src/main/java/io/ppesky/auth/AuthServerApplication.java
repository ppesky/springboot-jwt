package io.ppesky.auth;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@PostConstruct
	public void settingStart() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
