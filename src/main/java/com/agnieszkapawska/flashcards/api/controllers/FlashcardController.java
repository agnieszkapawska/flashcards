package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/flashcard")
public class FlashcardController {
    private FlashcardFacade flashcardFacade;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FlashcardResponseDto> saveFlashcard(@RequestBody FlashcardDto flashcardDto) {
        return new ResponseEntity<>(flashcardFacade.saveFlashcard(flashcardDto), HttpStatus.OK);
    }

}
