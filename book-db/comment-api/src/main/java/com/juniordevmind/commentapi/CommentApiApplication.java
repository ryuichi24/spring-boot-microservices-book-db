package com.juniordevmind.commentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.juniordevmind.shared.errors.RestResponseEntityExceptionHandler;

@Import(RestResponseEntityExceptionHandler.class)

@SpringBootApplication
public class CommentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentApiApplication.class, args);
	}

}
