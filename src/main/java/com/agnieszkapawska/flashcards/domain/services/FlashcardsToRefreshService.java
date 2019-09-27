package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRefresh;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardsToRefreshRepository;
import lombok.AllArgsConstructor;
import java.util.Optional;

@AllArgsConstructor
public class FlashcardsToRefreshService implements FlashcardsStorageService<FlashcardsToRefresh> {
    private FlashcardsToRefreshRepository flashcardsToRefreshRepository;

    @Override
    public FlashcardsToRefresh save(FlashcardsToRefresh flashcardToRefresh) {
        return flashcardsToRefreshRepository.save(flashcardToRefresh);
    }

    @Override
    public Optional<FlashcardsToRefresh> findByUserId(Long userId) {
        return flashcardsToRefreshRepository.findByUserId(userId);
    }

    @Override
    public FlashcardsToRefresh createObject(User user) {
        return new FlashcardsToRefresh(user);
    }

    public Optional<FlashcardsToRefresh> findByFlashcardId(Long flashcardId) {
        return flashcardsToRefreshRepository.findByFlashcardsId(flashcardId);
    }
}
