package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class FlashcardFacade {
    private FlashcardRepository flashcardRepository;
    private ModelMapper modelMapper;

    public FlashcardResponseDto saveFlashcard(FlashcardDto flashcardDto) {
        Flashcard flashcard = modelMapper.map(flashcardDto, Flashcard.class);
        Flashcard savedFlashcard = flashcardRepository.save(flashcard);
        return modelMapper.map(savedFlashcard, FlashcardResponseDto.class);
    }
}
