package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/flashcard")
public class FlashcardController {
    private FlashcardFacade flashcardFacade;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FlashcardSaveResponseDto> saveFlashcard(@RequestBody FlashcardSaveDto flashcardSaveDto) {
        return new ResponseEntity<>(flashcardFacade.saveFlashcard(flashcardSaveDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashcardSaveResponseDto> updateFlashcard(@RequestBody FlashcardSaveDto flashcardSaveDto, @PathVariable Long id) {
        return new ResponseEntity<>(flashcardFacade.updateFlashcard(flashcardSaveDto, id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FlashcardGetDto>> getAllFlashcards() {
        return new ResponseEntity<>(flashcardFacade.getAllFlashcards(), HttpStatus.OK);
    }

}
