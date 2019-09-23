package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToRepeatRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FlashcardsToRepeatService implements FlashcardsStorageService {

    private FlashcardsToRepeatRepository flashcardsToRepeatRepository;

    public FlashcardsToRepeat save(FlashcardsToRepeat flashcardsToRepeat) {
        return flashcardsToRepeatRepository.save(flashcardsToRepeat);
    }

    public FlashcardsToRepeat findByUserId(Long userId) {
        return flashcardsToRepeatRepository.findByUserId(userId).orElseThrow(
                () -> new EntityCouldNotBeFoundException("FlashcardToRepeat couldn't by found by user id: " + userId)
        );
    }
}
