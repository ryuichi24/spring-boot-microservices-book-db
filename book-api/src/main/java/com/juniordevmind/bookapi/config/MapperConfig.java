package com.juniordevmind.bookapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juniordevmind.bookapi.dtos.BookDto;
import com.juniordevmind.bookapi.models.Book;

@Configuration
public class MapperConfig {
    @Bean
    ModelMapper modelMapper() {
        // http://modelmapper.org/getting-started/#mapping
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .typeMap(Book.class, BookDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getAuthors(), BookDto::setAuthorIdList));
        return modelMapper;
    }
}
