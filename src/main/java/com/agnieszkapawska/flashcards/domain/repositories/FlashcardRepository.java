package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    Optional<Flashcard> findByQuestion(String question);
}
