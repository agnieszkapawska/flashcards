package com.agnieszkapawska.flashcards.domain.utils;

import com.agnieszkapawska.flashcards.domain.models.FlashcardsStorage;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsStorageService;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MarkAnswerData {
    private FlashcardsStorage flashcardsStorage;
    private FlashcardsStorageService currentFlashcardStorageService;
    private FlashcardsStorageService futureFlashcardsStorageService;
}
