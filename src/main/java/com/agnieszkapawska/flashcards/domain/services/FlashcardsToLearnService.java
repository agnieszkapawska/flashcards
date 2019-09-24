package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToLearnRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class FlashcardsToLearnService implements FlashcardsStorageService {
    private FlashcardsToLearnRepository flashcardsToLearnRepository;

    public FlashcardsToLearn save(FlashcardsToLearn flashcardsToLearn) {
        return flashcardsToLearnRepository.save(flashcardsToLearn);
    }

    public FlashcardsToLearn findByUserId(Long userId) {
        return flashcardsToLearnRepository.findByUserId(userId).orElseThrow(
                () -> new EntityCouldNotBeFoundException("User wit id: " + userId + " couldn't be found")
        );
    }

    public Optional<FlashcardsToLearn> returnFlashcardToLearnWhenContainsFlashcardWithId(Long flashcardId) {
        return flashcardsToLearnRepository.findByFlashcardsId(flashcardId);
    }
}
