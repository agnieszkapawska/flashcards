package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
public class FlashcardService {
    private FlashcardRepository flashcardRepository;

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public Flashcard findById(Long id) throws EntityNotFoundException {
        return flashcardRepository.findById(id).orElseThrow(
                () -> new EntityCouldNotBeFoundException("Flash card can't be found"));
    }
}
