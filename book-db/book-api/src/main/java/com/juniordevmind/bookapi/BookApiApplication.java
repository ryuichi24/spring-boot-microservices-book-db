package com.juniordevmind.bookapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.juniordevmind.shared.errors.RestResponseEntityExceptionHandler;

@Import(RestResponseEntityExceptionHandler.class)

@SpringBootApplication
public class BookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApiApplication.class, args);
	}

}
