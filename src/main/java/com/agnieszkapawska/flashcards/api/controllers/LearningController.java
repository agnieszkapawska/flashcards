package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.LearningFacade;
import com.agnieszkapawska.flashcards.domain.utils.Answer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/learning")
public class LearningController {
    private LearningFacade learningFacade;

    @GetMapping
    public ResponseEntity<List<FlashcardGetResponseDto>> getFlashcardsToLearnByUserId(@RequestParam(value = "userId")Long userId) {
        return new ResponseEntity<>(learningFacade.getFlashcardsToLearnByUserId(userId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> markAnswer(@RequestBody Answer answer) {
        learningFacade.markAnswer(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
