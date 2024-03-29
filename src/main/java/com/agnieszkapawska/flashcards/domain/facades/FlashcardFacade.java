package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardGetResponseDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToLearnService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import com.agnieszkapawska.flashcards.domain.services.authorization.UserService;
import com.agnieszkapawska.flashcards.domain.utils.CompareQuestionTagsSets;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FlashcardFacade {
    private FlashcardService flashcardService;
    private QuestionTagService questionTagService;
    private UserService userService;
    private FlashcardsToLearnService flashcardsToLearnService;
    private ModelMapper modelMapper;

    public FlashcardSaveResponseDto saveFlashcard(FlashcardSaveDto flashcardSaveDto) {
        Flashcard flashcard = modelMapper.map(flashcardSaveDto, Flashcard.class);
        if(flashcardSaveDto.getUserId() != null) {
            User user = userService.findById(flashcardSaveDto.getUserId());
            flashcard.setUser(user);
        } else {
            throw new EntityNotCreatedException("User id can't be empty ");
        }
        try {
            Flashcard savedFlashcard = flashcardService.saveFlashcard(flashcard);
            Set<QuestionTag> questionTagsSet = questionTagService.getQuestionTagsSet(flashcardSaveDto.getTagsSet());
            questionTagsSet.forEach(questionTag -> {
                questionTag.getFlashcards().add(savedFlashcard);
            });
            savedFlashcard.setQuestionTagsSet(questionTagsSet);
            flashcardService.saveFlashcard(savedFlashcard);
            FlashcardsToLearn flashcardsToLearn = flashcardsToLearnService
                    .findByUserId(flashcardSaveDto.getUserId())
                    .orElseGet(() -> {
                        FlashcardsToLearn newFlashcardsToLearn = new FlashcardsToLearn();
                        newFlashcardsToLearn.setFlashcards(new HashSet<>());
                        return newFlashcardsToLearn;
                    });
            flashcardsToLearn.getFlashcards().add(flashcard);
            flashcardsToLearnService.save(flashcardsToLearn);
            return modelMapper.map(savedFlashcard, FlashcardSaveResponseDto.class);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("constraint violation exception");
        } catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }
    }

    public FlashcardSaveResponseDto updateFlashcard(FlashcardSaveDto flashcardSaveDto, Long id) {
        Flashcard existingFlashcard = flashcardService.findById(id);

        CompareQuestionTagsSets tagsToUpdate =
                new CompareQuestionTagsSets(existingFlashcard.getQuestionTagsSet(), flashcardSaveDto.getTagsSet());

        Set<QuestionTag> questionTagsToAddSet = questionTagService.getQuestionTagsSet(tagsToUpdate.getTagsNamesToAdd());
        Set<QuestionTag> questionTagsToRemoveSet = questionTagService.getQuestionTagsSet(tagsToUpdate.getTagsNamesToRemove());
        Set<QuestionTag> questionTagsUseless =
                updateFlashcardSet(questionTagsToAddSet, questionTagsToRemoveSet, existingFlashcard);

        existingFlashcard.getQuestionTagsSet().addAll(questionTagsToAddSet);
        existingFlashcard.getQuestionTagsSet().removeAll(questionTagsToRemoveSet);

        existingFlashcard.update(flashcardSaveDto);
        try {
        flashcardService.saveFlashcard(existingFlashcard);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("constraint violation exception");
        } catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }

        if(!questionTagsUseless.isEmpty()) {
            questionTagService.deleteUselessQuestionTags(questionTagsUseless);
        }

        return modelMapper.map(existingFlashcard, FlashcardSaveResponseDto.class);
    }

    /*
        method update flashcardSets in questionTags
        and returns questionTags which have empty flashcardsSet and these questionTags can be removed from database
    */
    private Set<QuestionTag> updateFlashcardSet(Set<QuestionTag> tagsToAdd, Set<QuestionTag> tagsToRemove, Flashcard flashcard) {
        Set<QuestionTag> uselessQuestionTags = new HashSet<>();
        //add
        tagsToAdd.forEach(questionTag -> {
            questionTag.getFlashcards().add(flashcard);
        });
        //remove
        tagsToRemove.forEach(questionTag -> {
            questionTag.getFlashcards().remove(flashcard);
            if(questionTag.getFlashcards().isEmpty()) {
                uselessQuestionTags.add(questionTag);
            }
        });
        return uselessQuestionTags;
    }

    public List<FlashcardGetResponseDto> getFlashcards(Optional<String> searchPhrase, Optional<List<String>> tagsList) {
        List<Flashcard> flashcards;
        if(searchPhrase.isPresent()) {
            flashcards = flashcardService.findByPhrase(searchPhrase.get());
        } else if(tagsList.isPresent()) {
            flashcards = questionTagService.findFlashcardsByTags(tagsList.get());
        }
        else {
            flashcards = flashcardService.findAll();
        }
        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardGetResponseDto.class))
                .collect(Collectors.toList());
    }

    public FlashcardGetResponseDto getFlashcardById(Long id) {
        return modelMapper.map(flashcardService.findById(id), FlashcardGetResponseDto.class);
    }
}
