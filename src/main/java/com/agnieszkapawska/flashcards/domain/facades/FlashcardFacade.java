package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import com.agnieszkapawska.flashcards.domain.utils.CompareQuestionTagsSets;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.HashSet;
import java.util.Set;

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
                questionTagService.save(questionTag);
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
        Flashcard flashcardFound = flashcardService.findById(id);

        CompareQuestionTagsSets tagsToUpdate = compareQuestionTagSets(flashcardFound.getQuestionTagsSet(), flashcardDto.getTagsList());
        Set<QuestionTag> questionTagsUseless = questionTagService.updateFlashcardSet(tagsToUpdate, flashcardFound);

        Set<QuestionTag> questionTagsToAddSet = questionTagService.getQuestionTagsSet(tagsToUpdate.getTagsNamesToAdd());
        Set<QuestionTag> questionTagsToRemoveSet = questionTagService.getQuestionTagsSet(tagsToUpdate.getTagsNamesToRemove());
        flashcardFound.getQuestionTagsSet().addAll(questionTagsToAddSet);
        flashcardFound.getQuestionTagsSet().removeAll(questionTagsToRemoveSet);

        flashcardFound.setChanges(flashcardDto);
        try {
        flashcardService.saveFlashcard(flashcardFound);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("constraint violation exception");
        } catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }

        if(!questionTagsUseless.isEmpty()) {
            questionTagService.deleteUselessQuestionTags(questionTagsUseless);
        }

        return modelMapper.map(flashcardFound, FlashcardSaveResponseDto.class);
    }
    private CompareQuestionTagsSets compareQuestionTagSets(Set<QuestionTag> questionTagsSetBeforeChanges, Set<String> questionTagsNamesSetActual) {
        CompareQuestionTagsSets compareQuestionTagsSets = new CompareQuestionTagsSets();

        HashSet<String> tagsNamesSetBeforeChanges = new HashSet<>();
        questionTagsSetBeforeChanges.forEach(questionTag -> tagsNamesSetBeforeChanges.add(questionTag.getName()));

        Set<String> tagsToRemove = Sets.difference(tagsNamesSetBeforeChanges, questionTagsNamesSetActual);
        Set<String> tagsToAdd = Sets.difference(questionTagsNamesSetActual, tagsNamesSetBeforeChanges);
        compareQuestionTagsSets.setTagsNamesToRemove(tagsToRemove);
        compareQuestionTagsSets.setTagsNamesToAdd(tagsToAdd);

        return compareQuestionTagsSets;
    }

}
