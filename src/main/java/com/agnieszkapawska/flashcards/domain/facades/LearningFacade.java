package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToRepeatService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToLearnService;
import com.agnieszkapawska.flashcards.domain.utils.Answer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LearningFacade {
    private FlashcardsToLearnService flashcardsToLearnService;
    private FlashcardService flashcardService;
    private FlashcardsToRepeatService flashcardsToRepeatService;
    private ModelMapper modelMapper;

    public List<FlashcardGetResponseDto> getFlashcardsToLearnByUserId(Long userId) {
        FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService.findByUserId(userId);
        ArrayList<Flashcard> flashcards = new ArrayList<>(flashcardsToLearn.getFlashcards());
        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardGetResponseDto.class))
                .collect(Collectors.toList());
    }

    public void markAnswer(Answer answer) {
        Flashcard flashcard = flashcardService.findById(Long.parseLong(answer.getFlashcardId()));
        if(answer.getIsCorrect()) {
            if (flashcard.getCorrectAnswerCounter() < 2) {
                flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
            } else {
                FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService.findByUserId(Long.parseLong(answer.getUserId()));
                moveFlashcardFromFlashcardsToLearnToFlashcardsToRepeat(answer, flashcard, flashcardsToLearn);
            }
        } else {
            flashcard.setCorrectAnswerCounter(0);
        }
        flashcardService.saveFlashcard(flashcard);
    }

    private void moveFlashcardFromFlashcardsToLearnToFlashcardsToRepeat(Answer answer, Flashcard flashcard, FlashcardsToLearn flashcardsToLearn) {
        flashcardsToLearn.getFlashcards().remove(flashcard);
        flashcardsToLearnService.save(flashcardsToLearn);

        flashcard.setCorrectAnswerCounter(0);

        FlashcardsToRepeat flashcardsToRepeat = flashcardsToRepeatService.findByUserId(Long.parseLong(answer.getUserId()))
                .orElse(new FlashcardsToRepeat(flashcard.getUser()));
        Optional<Set<Flashcard>> flashcardsOptional = Optional.ofNullable(flashcardsToRepeat.getFlashcards());
        Set<Flashcard> flashcards = flashcardsOptional.orElse(new HashSet<>());

        flashcards.add(flashcard);
        flashcardsToRepeat.setFlashcards(flashcards);

        flashcardsToRepeatService.save(flashcardsToRepeat);
    }
}
