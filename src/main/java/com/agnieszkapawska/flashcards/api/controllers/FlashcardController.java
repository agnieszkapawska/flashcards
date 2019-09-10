package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/flashcard")
public class FlashcardController {
    private FlashcardFacade flashcardFacade;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FlashcardSaveResponseDto> saveFlashcard(@RequestBody FlashcardDto flashcardDto) {
        return new ResponseEntity<>(flashcardFacade.saveFlashcard(flashcardDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashcardSaveResponseDto> editFlashcard(@RequestBody FlashcardDto flashcardDto, @PathVariable Long id) {
        return new ResponseEntity<>(flashcardFacade.updateFlashcard(flashcardDto, id), HttpStatus.OK);
    }

}
