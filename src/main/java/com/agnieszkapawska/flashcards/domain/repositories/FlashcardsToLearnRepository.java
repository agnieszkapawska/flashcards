package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlashcardsToLearnRepository extends JpaRepository<FlashcardsToLearn, Long> {
    Optional<FlashcardsToLearn> findByUserId(Long userId);
    Optional<FlashcardsToLearn> findByFlashcardsId(Long flashcardId);
}
