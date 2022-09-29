package com.juniordevmind.authorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.juniordevmind.shared.errors.RestResponseEntityExceptionHandler;

// https://stackoverflow.com/a/64490949/13723015
@Import({ RestResponseEntityExceptionHandler.class })
@SpringBootApplication
public class AuthorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorApiApplication.class, args);
	}

}
