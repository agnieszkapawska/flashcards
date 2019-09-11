package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import com.agnieszkapawska.flashcards.domain.utils.CompareQuestionTagsSets;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FlashcardFacade {
    private FlashcardService flashcardService;
    private QuestionTagService questionTagService;
    private ModelMapper modelMapper;

    public FlashcardSaveResponseDto saveFlashcard(FlashcardDto flashcardDto) {
        Flashcard flashcard = modelMapper.map(flashcardDto, Flashcard.class);
        try {
            Flashcard savedFlashcard = flashcardService.saveFlashcard(flashcard);
            Set<QuestionTag> questionTagsSet = questionTagService.getQuestionTagsSet(flashcardDto.getTagsList());
            questionTagsSet.forEach(questionTag -> {
                questionTag.getFlashcards().add(savedFlashcard);
            });
            savedFlashcard.setQuestionTagsSet(questionTagsSet);
            flashcardService.saveFlashcard(savedFlashcard);
            return modelMapper.map(savedFlashcard, FlashcardSaveResponseDto.class);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("constraint violation exception");
        } catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }
    }

    public FlashcardSaveResponseDto updateFlashcard(FlashcardDto flashcardDto, Long id) {
        Flashcard existingFlashcard = flashcardService.findById(id);

        CompareQuestionTagsSets tagsToUpdate =
                new CompareQuestionTagsSets(existingFlashcard.getQuestionTagsSet(), flashcardDto.getTagsList());

        Set<QuestionTag> questionTagsToAddSet = questionTagService.getQuestionTagsSet(tagsToUpdate.getTagsNamesToAdd());
        Set<QuestionTag> questionTagsToRemoveSet = questionTagService.getQuestionTagsSet(tagsToUpdate.getTagsNamesToRemove());
        Set<QuestionTag> questionTagsUseless =
                updateFlashcardSet(questionTagsToAddSet, questionTagsToRemoveSet, existingFlashcard);

        existingFlashcard.getQuestionTagsSet().addAll(questionTagsToAddSet);
        existingFlashcard.getQuestionTagsSet().removeAll(questionTagsToRemoveSet);

        existingFlashcard.setChanges(flashcardDto);
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

    public List<FlashcardDto> getAllFlashcards() {
        List<Flashcard> allFlashcards = flashcardService.findAll();
        return allFlashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardDto.class))
                .collect(Collectors.toList());
    }
}
