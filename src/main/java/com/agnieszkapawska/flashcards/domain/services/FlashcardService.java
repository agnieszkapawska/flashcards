package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FlashcardService {
    private FlashcardRepository flashcardRepository;

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }
}
