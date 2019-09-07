package com.agnieszkapawska.flashcards.infrastructure.configurations;

import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlashcardConfiguration {

    @Bean
    public FlashcardFacade flashcardFacade(FlashcardRepository flashcardRepository, ModelMapper modelMapper) {
        return new FlashcardFacade(flashcardRepository, modelMapper);
    }
}
