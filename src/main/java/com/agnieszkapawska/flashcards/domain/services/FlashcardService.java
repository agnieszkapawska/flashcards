package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

    public List<Flashcard> findAll() {
        return flashcardRepository.findAll();
    }
}
