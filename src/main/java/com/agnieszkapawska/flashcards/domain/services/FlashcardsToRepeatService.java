package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToRepeatRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class FlashcardsToRepeatService implements FlashcardsStorageService<FlashcardsToRepeat> {

    private FlashcardsToRepeatRepository flashcardsToRepeatRepository;

    @Override
    public FlashcardsToRepeat save(FlashcardsToRepeat flashcardsToRepeat) {
        return flashcardsToRepeatRepository.save(flashcardsToRepeat);
    }

    @Override
    public Optional<FlashcardsToRepeat> findByUserId(Long userId) {
        return flashcardsToRepeatRepository.findByUserId(userId);
    }

    @Override
    public FlashcardsToRepeat createObject(User user) {
        return new FlashcardsToRepeat(user);
    }

    public Optional<FlashcardsToRepeat> returnFlashcardToLearnWhenContainsFlashcardWithId(Long flashcardId) {
        return flashcardsToRepeatRepository.findByFlashcardsId(flashcardId);
    }

}
