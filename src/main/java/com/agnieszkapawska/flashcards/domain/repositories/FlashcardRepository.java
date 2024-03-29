package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    Optional<List<Flashcard>> findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(String searchPhrase, String searchPhrase2);
}
