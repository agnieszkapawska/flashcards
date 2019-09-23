package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/flashcards")
public class FlashcardController {
    private FlashcardFacade flashcardFacade;

    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlashcardSaveResponseDto> saveFlashcard(@RequestBody FlashcardSaveDto flashcardSaveDto) {
        return new ResponseEntity<>(flashcardFacade.saveFlashcard(flashcardSaveDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashcardSaveResponseDto> updateFlashcard(@RequestBody FlashcardSaveDto flashcardSaveDto, @PathVariable Long id) {
        return new ResponseEntity<>(flashcardFacade.updateFlashcard(flashcardSaveDto, id), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FlashcardGetResponseDto>> getFlashcards(
            @RequestParam(value = "searchPhrase", required = false)String searchPhrase,
            @RequestParam(value = "tagsSet", required = false)List<String> tagsList
            ) {
        Optional<String> searchPhraseOptional = Optional.ofNullable(searchPhrase);
        Optional<List<String>> tagsListOptional = Optional.ofNullable(tagsList);
        return new ResponseEntity<>(flashcardFacade.getFlashcards(searchPhraseOptional, tagsListOptional), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashcardGetResponseDto> getFlashcardById(@PathVariable Long id) {
        return new ResponseEntity<>(flashcardFacade.getFlashcardById(id), HttpStatus.OK);
    }

    @GetMapping("/learn")
    public ResponseEntity<List<FlashcardGetResponseDto>> getFlashcardsToLearnByUserId(@RequestParam(value = "userId")Long userId) {
        return new ResponseEntity<>(flashcardFacade.getFlashcardsToLearnByUserId(userId), HttpStatus.OK);
    }
}
