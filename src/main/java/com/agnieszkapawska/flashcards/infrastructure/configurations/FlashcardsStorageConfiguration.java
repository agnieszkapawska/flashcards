package com.agnieszkapawska.flashcards.infrastructure.configurations;

import com.agnieszkapawska.flashcards.domain.facades.LearningFacade;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToLearnRepository;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToRepeatRepository;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToLearnService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToRepeatService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlashcardsStorageConfiguration {
    @Bean
    FlashcardsToLearnService flashcardsToLearnService(FlashcardsToLearnRepository flashcardsToLearnRepository) {
        return new FlashcardsToLearnService(flashcardsToLearnRepository);
    }

    @Bean
    LearningFacade learningFacade(
            FlashcardsToLearnService flashcardsToLearnService,
            FlashcardService flashcardService,
            FlashcardsToRepeatService flashcardsToRepeatService,
            ModelMapper modelMapper) {
        return new LearningFacade(flashcardsToLearnService, flashcardService, flashcardsToRepeatService, modelMapper);
    }

    @Bean
    FlashcardsToRepeatService flashcardToRepeatService(FlashcardsToRepeatRepository flashcardsToRepeatRepository) {
        return new FlashcardsToRepeatService(flashcardsToRepeatRepository);
    }
}
