package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.User;

import java.util.Optional;

public interface FlashcardsStorageService<FlashcardsStorage> {
    FlashcardsStorage save(FlashcardsStorage object);
    Optional<FlashcardsStorage> findByUserId(Long userId);
    FlashcardsStorage createObject(User user);
}
