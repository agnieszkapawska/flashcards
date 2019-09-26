package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToLearnRepository;
import lombok.AllArgsConstructor;
import java.util.Optional;

@AllArgsConstructor
public class FlashcardsToLearnService implements FlashcardsStorageService<FlashcardsToLearn> {
    private FlashcardsToLearnRepository flashcardsToLearnRepository;

    @Override
    public FlashcardsToLearn save(FlashcardsToLearn flashcardsToLearn) {
        return flashcardsToLearnRepository.save(flashcardsToLearn);
    }

    @Override
    public Optional<FlashcardsToLearn> findByUserId(Long userId) {
        return flashcardsToLearnRepository.findByUserId(userId);
    }

    @Override
    public FlashcardsToLearn createObject(User user) {
        return new FlashcardsToLearn(user);
    }

    public Optional<FlashcardsToLearn> returnFlashcardToLearnWhenContainsFlashcardWithId(Long flashcardId) {
        return flashcardsToLearnRepository.findByFlashcardsId(flashcardId);
    }
}
