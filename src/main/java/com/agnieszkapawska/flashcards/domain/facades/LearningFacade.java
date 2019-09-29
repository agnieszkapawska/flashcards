package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.models.*;
import com.agnieszkapawska.flashcards.domain.services.*;
import com.agnieszkapawska.flashcards.domain.utils.Answer;
import com.agnieszkapawska.flashcards.domain.utils.MarkAnswerData;
import org.modelmapper.ModelMapper;
import java.util.*;
import java.util.stream.Collectors;

public class LearningFacade {
    private FlashcardsToLearnService flashcardsToLearnService;
    private FlashcardService flashcardService;
    private FlashcardsToRepeatService flashcardsToRepeatService;
    private FlashcardsToRefreshService flashcardsToRefreshService;
    private ModelMapper modelMapper;

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
        FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService.findByUserId(userId)
                .orElseGet(FlashcardsToLearn::new);
        ArrayList<Flashcard> flashcards = new ArrayList<>(flashcardsToLearn.getFlashcards());
        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardGetResponseDto.class))
                .collect(Collectors.toList());
    }

    public void markAnswer(Answer answer) {
        Long flashcardId = answer.getFlashcardId();
        Flashcard flashcard = flashcardService.findById(flashcardId);

        Optional<FlashcardsToLearn> flashcardsToLearnOptional = flashcardsToLearnService.findByFlashcardId(flashcardId);
        if(flashcardsToLearnOptional.isPresent()) {
            MarkAnswerData markAnswerData = new MarkAnswerData(flashcardsToLearnOptional.get(), flashcardsToLearnService, flashcardsToRepeatService);
            markAnswer(answer, flashcard, markAnswerData);
            flashcardService.saveFlashcard(flashcard);
            return;
        }

        Optional<FlashcardsToRepeat> flashcardsToRepeatOptional = flashcardsToRepeatService.findByFlashcardIs(flashcardId);
        if(flashcardsToRepeatOptional.isPresent()) {
            MarkAnswerData markAnswerData = new MarkAnswerData(flashcardsToRepeatOptional.get(), flashcardsToRepeatService, flashcardsToRefreshService);
            markAnswer(answer, flashcard, markAnswerData);
            flashcardService.saveFlashcard(flashcard);
            return;
        }

        Optional<FlashcardsToRefresh> flashcardsToRefreshOptional = flashcardsToRefreshService.findByFlashcardId(flashcardId);
        if(flashcardsToRefreshOptional.isPresent()) {
            MarkAnswerData markAnswerData = new MarkAnswerData(flashcardsToRefreshOptional.get(), flashcardsToRefreshService, flashcardsToRefreshService);
            markAnswer(answer, flashcard, markAnswerData);
            flashcardService.saveFlashcard(flashcard);
        }
    }

    private void markAnswer(
            Answer answer,
            Flashcard flashcard,
            MarkAnswerData markAnswerData) {
        Long userId = answer.getUserId();
        if(answer.getIsCorrect()) {
            upgradeFlashcard(flashcard, markAnswerData, userId);
        } else {
            downgradeFlashcard(flashcard, markAnswerData, userId);
        }
    }

    private void downgradeFlashcard(Flashcard flashcard, MarkAnswerData markAnswerData, Long userId) {
        flashcard.setCorrectAnswerCounter(0);
        if (markAnswerData.getFlashcardsStorage() instanceof FlashcardsToLearn) {
            return;
        }
        moveFlashcardFromOneFlashcardsStorageToAnother(
                userId, flashcard, markAnswerData.getFlashcardsStorage(),
                markAnswerData.getCurrentFlashcardStorageService(), flashcardsToLearnService);
    }

    private void upgradeFlashcard(Flashcard flashcard, MarkAnswerData markAnswerData, Long userId) {
        if (flashcard.getCorrectAnswerCounter() < 2) {
            flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
        } else {
            if(markAnswerData.getFlashcardsStorage() instanceof FlashcardsToRefresh) {
                flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
                return;
            }
            moveFlashcardFromOneFlashcardsStorageToAnother(
                    userId, flashcard, markAnswerData.getFlashcardsStorage(),
                    markAnswerData.getCurrentFlashcardStorageService(),
                    markAnswerData.getFutureFlashcardsStorageService());
        }
    }

    private void moveFlashcardFromOneFlashcardsStorageToAnother(
            Long userId, Flashcard flashcard, FlashcardsStorage fromWhichFlashcardsStorageIsMoved,
            FlashcardsStorageService currentFlashcardsStorageService, FlashcardsStorageService futureFlashcardsStorageService) {

        fromWhichFlashcardsStorageIsMoved.getFlashcards().remove(flashcard);
        currentFlashcardsStorageService.save(fromWhichFlashcardsStorageIsMoved);

        FlashcardsStorage toWhichFlashcardsStorageIsMoved = (FlashcardsStorage) futureFlashcardsStorageService.findByUserId(userId)
                .orElseGet( () -> futureFlashcardsStorageService.createObject(flashcard.getUser()));
        flashcard.setCorrectAnswerCounter(0);

        Set<Flashcard> flashcards = Optional.ofNullable(toWhichFlashcardsStorageIsMoved.getFlashcards())
                .orElseGet(HashSet::new);
        flashcards.add(flashcard);
        toWhichFlashcardsStorageIsMoved.setFlashcards(flashcards);
        futureFlashcardsStorageService.save(toWhichFlashcardsStorageIsMoved);
    }
}
