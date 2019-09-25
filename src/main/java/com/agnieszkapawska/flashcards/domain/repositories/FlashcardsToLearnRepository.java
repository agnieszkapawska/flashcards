package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import java.util.Optional;

public interface FlashcardsToLearnRepository extends MyGenericRepository<FlashcardsToLearn> {
    Optional<FlashcardsToLearn> findByUserId(Long userId);
    Optional<FlashcardsToLearn> findByFlashcardsId(Long flashcardId);
}
