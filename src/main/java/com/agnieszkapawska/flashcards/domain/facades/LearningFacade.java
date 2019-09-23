package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToLearnService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LearningFacade {
    private FlashcardsToLearnService flashcardsToLearnService;
    private ModelMapper modelMapper;

    public List<FlashcardGetResponseDto> getFlashcardsToLearnByUserId(Long userId) {
        FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService.findByUserId(userId);
        ArrayList<Flashcard> flashcards = new ArrayList<>(flashcardsToLearn.getFlashcards());
        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardGetResponseDto.class))
                .collect(Collectors.toList());
    }
}
