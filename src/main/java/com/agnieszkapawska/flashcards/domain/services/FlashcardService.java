package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Flashcard> findByPhrase(String searchPhrase) {
        List<Flashcard> flashcardsContainsSearchPhrase = new ArrayList<>();
        flashcardRepository.findByQuestionContaining(searchPhrase)
                .ifPresent(flashcards -> flashcardsContainsSearchPhrase.addAll(flashcards));
        flashcardRepository.findByAnswerContaining(searchPhrase)
                .ifPresent(flashcards -> flashcardsContainsSearchPhrase.addAll(flashcards));
        return flashcardsContainsSearchPhrase;
    }
}
