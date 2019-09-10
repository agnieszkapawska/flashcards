package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@AllArgsConstructor
public class FlashcardService {
    private FlashcardRepository flashcardRepository;

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public Flashcard findById(Long id) throws EntityNotFoundException {
        Optional<Flashcard> flashCardOptional = flashcardRepository.findById(id);
        if(flashCardOptional.isPresent()) {
            return flashCardOptional.get();
        } else {
           throw new com.agnieszkapawska.flashcards.domain.exceptions.EntityNotFoundException ("Entity not found");
        }
    }

}
