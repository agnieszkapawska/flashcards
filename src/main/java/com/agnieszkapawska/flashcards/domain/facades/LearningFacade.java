package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRefresh;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToRepeat;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToLearnService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToRefreshService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToRepeatService;
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
        FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService.findByUserId(userId);
        ArrayList<Flashcard> flashcards = new ArrayList<>(flashcardsToLearn.getFlashcards());
        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardGetResponseDto.class))
                .collect(Collectors.toList());
    }

    public void markAnswer(Answer answer) {
        Long flashcardId = Long.parseLong(answer.getFlashcardId());
        shouldReturnFromMethod = false;
        Flashcard flashcard = flashcardService.findById(Long.parseLong(answer.getFlashcardId()));
        flashcardsToLearnService.returnFlashcardToLearnWhenContainsFlashcardWithId(flashcardId)
                .ifPresent(flashcardsToLearn -> {
                    markAnswerIfFlashcardIsToLearn(answer, flashcard, flashcardsToLearn);
                    flashcardService.saveFlashcard(flashcard);
                });
        if (shouldReturnFromMethod) {
            return;
        }
        flashcardsToRepeatService.returnFlashcardToLearnWhenContainsFlashcardWithId(flashcardId)
                .ifPresent(flashcardsToRepeat -> {
                    markAnswerIfFlashcardIsToRepeat(answer, flashcard, flashcardsToRepeat);
                    flashcardService.saveFlashcard(flashcard);
                });


    }

    private void markAnswerIfFlashcardIsToLearn(Answer answer, Flashcard flashcard, FlashcardsToLearn flashcardsToLearn) {
        if(answer.getIsCorrect()) {
            if (flashcard.getCorrectAnswerCounter() < 2) {
                flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
            } else {
                moveFlashcardFromFlashcardsToLearnToFlashcardsToRepeat(answer, flashcard, flashcardsToLearn);
            }
        } else {
            flashcard.setCorrectAnswerCounter(0);
        }
        shouldReturnFromMethod = true;
    }

    private void markAnswerIfFlashcardIsToRepeat(Answer answer, Flashcard flashcard, FlashcardsToRepeat flashcardsToRepeat) {
        if(answer.getIsCorrect()) {
            if(flashcard.getCorrectAnswerCounter() < 2) {
                flashcard.setCorrectAnswerCounter(flashcard.getCorrectAnswerCounter() + 1);
            } else {
                moveFlashcardFromFlashcardToRepeatToFlashcardToRefresh(answer, flashcard, flashcardsToRepeat);
            }
        }
    }

    private void moveFlashcardFromFlashcardToRepeatToFlashcardToRefresh(Answer answer, Flashcard flashcard, FlashcardsToRepeat flashcardsToRepeat) {
        Long userId = Long.parseLong(answer.getUserId());

        flashcardsToRepeat.getFlashcards().remove(flashcard);
        flashcardsToRepeatService.save(flashcardsToRepeat);

        flashcard.setCorrectAnswerCounter(0);

        FlashcardsToRefresh flashcardsToRefresh = flashcardsToRefreshService.findByUserId(userId)
                .orElse(new FlashcardsToRefresh(flashcard.getUser()));
        Set<Flashcard> flashcards = Optional.ofNullable(flashcardsToRefresh.getFlashcards())
                .orElse(new HashSet<>());

        flashcards.add(flashcard);
        flashcardsToRefresh.setFlashcards(flashcards);

        flashcardsToRefreshService.save(flashcardsToRefresh);
    }

    private void moveFlashcardFromFlashcardsToLearnToFlashcardsToRepeat(Answer answer, Flashcard flashcard, FlashcardsToLearn flashcardsToLearn) {
        flashcardsToLearn.getFlashcards().remove(flashcard);
        flashcardsToLearnService.save(flashcardsToLearn);

        flashcard.setCorrectAnswerCounter(0);

        FlashcardsToRepeat flashcardsToRepeat = flashcardsToRepeatService.findByUserId(Long.parseLong(answer.getUserId()))
                .orElse(new FlashcardsToRepeat(flashcard.getUser()));
        Set<Flashcard> flashcards = Optional.ofNullable(flashcardsToRepeat.getFlashcards())
                .orElse(new HashSet<>());

        flashcards.add(flashcard);
        flashcardsToRepeat.setFlashcards(flashcards);

        flashcardsToRepeatService.save(flashcardsToRepeat);
    }
}
