package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FlashcardsToRefreshRepository extends JpaRepository<FlashcardsToRefresh, Long> {
    Optional<FlashcardsToRefresh> findByUserId(Long userId);
    Optional<FlashcardsToRefresh> findByFlashcardsId(Long flashcardId);
}
