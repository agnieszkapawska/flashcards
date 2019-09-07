package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

@AllArgsConstructor
public class FlashcardFacade {
    private FlashcardRepository flashcardRepository;
    private ModelMapper modelMapper;

    public FlashcardSaveResponseDto saveFlashcard(FlashcardDto flashcardDto) {
        Flashcard flashcard = modelMapper.map(flashcardDto, Flashcard.class);
        try{
            Flashcard savedFlashcard = flashcardRepository.save(flashcard);
            return modelMapper.map(savedFlashcard, FlashcardSaveResponseDto.class);
        }catch (DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("constraint violation exception");
        }catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }
    }
}
