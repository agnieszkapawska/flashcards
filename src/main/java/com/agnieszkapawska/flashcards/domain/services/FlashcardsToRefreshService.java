package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRefresh;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToRefreshRepository;
import lombok.AllArgsConstructor;
import java.util.Optional;

@AllArgsConstructor
public class FlashcardsToRefreshService {
    private FlashcardsToRefreshRepository flashcardsToRefreshRepository;

    public FlashcardsToRefresh save(FlashcardsToRefresh flashcardToRefresh) {
        return flashcardsToRefreshRepository.save(flashcardToRefresh);
    }

    public Optional<FlashcardsToRefresh> findByUserId(Long userId) {
        return flashcardsToRefreshRepository.findByUserId(userId);
    }

    public Optional<FlashcardsToRefresh> returnFlashcardToLearnWhenContainsFlashcardWithId(Long flashcardId) {
        return flashcardsToRefreshRepository.findByFlashcardsId(flashcardId);
    }
}
