package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public FlashcardSaveResponseDto editFlashcard(FlashcardDto flashcardDto, Long id) {
        Flashcard flashcardFound = flashcardService.findById(id);
        Map<String, Set<String>> tagsToUpdate = compareQuestionTagSets(flashcardFound.getQuestionTagsSet(), flashcardDto.getTagsList());
        questionTagService.updateFlashcardSet(tagsToUpdate, flashcardFound);
        flashcardFound.setChanges(flashcardDto);
        flashcardService.saveFlashcard(flashcardFound);
        return modelMapper.map(flashcardFound, FlashcardSaveResponseDto.class);
    }
    private Map<String, Set<String>> compareQuestionTagSets(Set<QuestionTag> questionTagsList, Set<String> tagsListEditFlashcard) {
        Set<String> tagsListSavedFlashcard = new HashSet<>();
        Map<String, Set<String>> tagsToRemoveAndAdd = new HashMap<>();
        for (QuestionTag questionTag:questionTagsList) {
            tagsListSavedFlashcard.add(questionTag.getName());
        }
        Set<String> tagsToRemove = (Set) Sets.difference(tagsListSavedFlashcard, tagsListEditFlashcard);
        Set<String> tagsToAdd = (Set) Sets.difference(tagsListEditFlashcard, tagsListSavedFlashcard);
        tagsToRemoveAndAdd.put("tagsToRemove", tagsToRemove);
        tagsToRemoveAndAdd.put("tagsToAdd", tagsToAdd);
        return tagsToRemoveAndAdd;
    }

}
