package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToRepeatRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class FlashcardsToRepeatService implements FlashcardsStorageService {

    private FlashcardsToRepeatRepository flashcardsToRepeatRepository;

    public FlashcardsToRepeat save(FlashcardsToRepeat flashcardsToRepeat) {
        return flashcardsToRepeatRepository.save(flashcardsToRepeat);
    }

    public Optional<FlashcardsToRepeat> findByUserId(Long userId) {
        return flashcardsToRepeatRepository.findByUserId(userId);
    }

    public Optional<FlashcardsToRepeat> returnFlashcardToLearnWhenContainsFlashcardWithId(Long flashcardId) {
        return flashcardsToRepeatRepository.findByFlashcardsId(flashcardId);
    }
}
