package com.agnieszkapawska.flashcards.infrastructure.configurations;

import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class FlashcardConfiguration {

    @Bean
    public FlashcardFacade flashcardFacade(FlashcardService flashcardService, QuestionTagService questionTagService, ModelMapper modelMapper) {
        return new FlashcardFacade(flashcardService, questionTagService, modelMapper);
    }

    @Bean
    public FlashcardService flashcardService(FlashcardRepository flashcardRepository) {
        return new FlashcardService(flashcardRepository);
    }

    @Bean
    public QuestionTagService questionTagService(QuestionTagRepository questionTagRepository) {
        return new QuestionTagService(questionTagRepository);
    }
}
