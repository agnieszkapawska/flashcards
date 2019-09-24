package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToRepeatService;
import com.agnieszkapawska.flashcards.domain.utils.Answer;
import lombok.AllArgsConstructor;

import java.util.Optional;

//@AllArgsConstructor
//public class RepeatingFacade {
//    private FlashcardsToRepeatService flashcardsToRepeatService;
//    private FlashcardService flashcardService;
//
//    public void markAnswer(Answer answer) {
//        Flashcard flashcard = flashcardService.findById(Long.parseLong(answer.getFlashcardId()));
//        if(answer.getIsCorrect()) {
//            if(flashcard.getCorrectAnswerCounter() < 2) {
//                flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
//            } else {
//                flashcard.setCorrectAnswerCounter(0);
//                FlashcardsToRepeat flashcardToRepeat = flashcardsToRepeatService.findByUserId(Long.parseLong(answer.getUserId())).get();
//                flashcardToRepeat.getFlashcards().remove(flashcard);
//            }
//        }
//        flashcardService.saveFlashcard(flashcard);
//    }
//}
