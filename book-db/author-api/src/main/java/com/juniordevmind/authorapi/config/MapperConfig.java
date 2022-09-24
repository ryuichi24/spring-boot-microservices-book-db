package com.juniordevmind.authorapi.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juniordevmind.authorapi.dtos.AuthorDto;
import com.juniordevmind.authorapi.models.Author;

@Configuration
public class MapperConfig {
    @Bean
    ModelMapper modelMapper() {
        // http://modelmapper.org/getting-started/#mapping
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .typeMap(Author.class, AuthorDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getBooks(), AuthorDto::setBookIdList));
        // // https://stackoverflow.com/questions/47818821/modelmapper-null-value-skip
        // modelMapper.getConfiguration().setSkipNullEnabled(true);
        // // https://stackoverflow.com/questions/45451025/how-to-exclude-whole-property-if-they-are-null-from-modelmapper
        // modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        // modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }
}
