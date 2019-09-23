package com.agnieszkapawska.flashcards.infrastructure.configurations;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<FlashcardSaveDto, Flashcard> typeMap = modelMapper.createTypeMap(FlashcardSaveDto.class, Flashcard.class);
        typeMap.addMappings(mapper -> mapper.skip(Flashcard::setId));
        return modelMapper;
    }
}
