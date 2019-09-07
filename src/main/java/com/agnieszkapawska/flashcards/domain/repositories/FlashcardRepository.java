package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

}
