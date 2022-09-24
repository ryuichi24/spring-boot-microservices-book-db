package com.juniordevmind.authorapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    ModelMapper modelMapper() {
        // http://modelmapper.org/getting-started/#mapping
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
