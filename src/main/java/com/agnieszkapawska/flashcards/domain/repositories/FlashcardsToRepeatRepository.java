package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlashcardsToRepeatRepository extends JpaRepository<FlashcardsToRepeat, Long> {
    Optional<FlashcardsToRepeat> findByUserId(Long userId);
}
