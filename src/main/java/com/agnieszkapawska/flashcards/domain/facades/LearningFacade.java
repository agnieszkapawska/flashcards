package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsStorage;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRefresh;
import com.agnieszkapawska.flashcards.domain.services.*;
import com.agnieszkapawska.flashcards.domain.utils.Answer;
import org.modelmapper.ModelMapper;
import java.util.*;
import java.util.stream.Collectors;

public class LearningFacade {
    private FlashcardsToLearnService flashcardsToLearnService;
    private FlashcardService flashcardService;
    private FlashcardsToRepeatService flashcardsToRepeatService;
    private FlashcardsToRefreshService flashcardsToRefreshService;
    private ModelMapper modelMapper;
    private boolean shouldReturnFromMethod;

    public LearningFacade(FlashcardsToLearnService flashcardsToLearnService,
                          FlashcardService flashcardService,
                          FlashcardsToRepeatService flashcardsToRepeatService,
                          FlashcardsToRefreshService flashcardsToRefreshService,
                          ModelMapper modelMapper) {
        this.flashcardsToLearnService = flashcardsToLearnService;
        this.flashcardService = flashcardService;
        this.flashcardsToRepeatService = flashcardsToRepeatService;
        this.flashcardsToRefreshService = flashcardsToRefreshService;
        this.modelMapper = modelMapper;
    }

    public List<FlashcardGetResponseDto> getFlashcardsToLearnByUserId(Long userId) {
        FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService.findByUserId(userId).get();
        ArrayList<Flashcard> flashcards = new ArrayList<>(flashcardsToLearn.getFlashcards());
        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardGetResponseDto.class))
                .collect(Collectors.toList());
    }

    public void markAnswer(Answer answer) {
        Long flashcardId = Long.parseLong(answer.getFlashcardId());
        shouldReturnFromMethod = false;
        Flashcard flashcard = flashcardService.findById(flashcardId);

        flashcardsToLearnService.findByFlashcardId(flashcardId)
                .ifPresent(flashcardsToLearn -> {
                    markAnswer(answer, flashcard, flashcardsToLearn, flashcardsToLearnService, flashcardsToRepeatService);
                    flashcardService.saveFlashcard(flashcard);
                });
        if (shouldReturnFromMethod) {
            return;
        }

        flashcardsToRepeatService.findByFlashcardIs(flashcardId)
                .ifPresent(flashcardsToRepeat -> {
                    markAnswer(answer, flashcard, flashcardsToRepeat, flashcardsToRepeatService, flashcardsToRefreshService);
                    flashcardService.saveFlashcard(flashcard);
                });
        if (shouldReturnFromMethod) {
            return;
        }

        flashcardsToRefreshService.findByFlashcardId(flashcardId)
                .ifPresent(flashcardsToRefresh -> {
                    markAnswer(answer, flashcard, flashcardsToRefresh, flashcardsToRefreshService, flashcardsToRefreshService);
                    flashcardService.saveFlashcard(flashcard);
                });
    }

    private void markAnswer(
            Answer answer,
            Flashcard flashcard,
            FlashcardsStorage flashcardsStorage,
            FlashcardsStorageService currentFlashcardsStorageService,
            FlashcardsStorageService futureFlashcardsStorageService) {

        if(answer.getIsCorrect()) {
            if (flashcard.getCorrectAnswerCounter() < 2) {
                flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
            } else {
                if(flashcardsStorage instanceof FlashcardsToRefresh) {
                    shouldReturnFromMethod = true;
                    return;
                }
                moveFlashcardFromOneFlashcardsStorageToAnother(
                        answer, flashcard, flashcardsStorage, currentFlashcardsStorageService, futureFlashcardsStorageService);
            }
        } else {
            flashcard.setCorrectAnswerCounter(0);
            if (flashcardsStorage instanceof FlashcardsToLearn) {
                shouldReturnFromMethod = true;
                return;
            }
            moveFlashcardFromOneFlashcardsStorageToAnother(
                    answer, flashcard, flashcardsStorage, currentFlashcardsStorageService, flashcardsToLearnService);
        }
        shouldReturnFromMethod = true;
    }

    private void moveFlashcardFromOneFlashcardsStorageToAnother(
            Answer answer, Flashcard flashcard, FlashcardsStorage fromWhichFlashcardsStorageIsMoved,
            FlashcardsStorageService currentFlashcardsStorageService, FlashcardsStorageService futureFlashcardsStorageService) {
        Long userId = Long.parseLong(answer.getUserId());

        fromWhichFlashcardsStorageIsMoved.getFlashcards().remove(flashcard);
        currentFlashcardsStorageService.save(fromWhichFlashcardsStorageIsMoved);

        FlashcardsStorage toWhichFlashcardsStorageIsMoved = (FlashcardsStorage) futureFlashcardsStorageService.findByUserId(userId)
                .orElse(
                        futureFlashcardsStorageService.createObject(flashcard.getUser())
                );
        flashcard.setCorrectAnswerCounter(0);

        Set<Flashcard> flashcards = Optional.ofNullable(toWhichFlashcardsStorageIsMoved.getFlashcards())
                .orElse(new HashSet<>());
        flashcards.add(flashcard);
        toWhichFlashcardsStorageIsMoved.setFlashcards(flashcards);
        futureFlashcardsStorageService.save(toWhichFlashcardsStorageIsMoved);
    }
}
