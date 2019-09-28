package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.FlashcardAndQuestionTagAbstractTests;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import java.util.stream.Collectors;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuestionTagServiceTest extends FlashcardAndQuestionTagAbstractTests {
    @MockBean
    private QuestionTagRepository questionTagRepository;
    @Autowired
    private QuestionTagService questionTagService;
    private List<String> tagsNamesList = Collections.singletonList("home");
    private Set<String> tagsNamesSet = new HashSet<>(Arrays.asList("home", "holidays"));

    @Test
    public void getQuestionTagsSet_ShouldReturnTagsFromRepository_WhenTagsExist() {
        //given
        when(questionTagRepository.findByName(any(String.class)))
                .thenReturn(Optional.of(new QuestionTag("home")))
                .thenReturn(Optional.of(new QuestionTag("holiday")));
        Set<QuestionTag> expectedQuestionTagSet = new HashSet<>(Arrays.asList(new QuestionTag("home"), new QuestionTag("holiday")));
        //when
        Set<QuestionTag> questionTagsSet = questionTagService.getQuestionTagsSet(tagsNamesSet);
        //then
        Assert.assertEquals(expectedQuestionTagSet, questionTagsSet);
    }

    @Test
    public void getQuestionTagsSet_ShouldReturnCreatedTags_WhenTagDoNotExist() {
        //given
        when(questionTagRepository.findByName(any(String.class)))
                .thenReturn(Optional.empty());
        //when
        Set<QuestionTag> questionTagsSet = questionTagService.getQuestionTagsSet(tagsNamesSet);
        Set<String> newTagsNames = questionTagsSet.stream()
                .map(QuestionTag::getName)
                .collect(Collectors.toSet());
        //then
        Assert.assertEquals(2, questionTagsSet.size());
        Assert.assertEquals(tagsNamesSet, newTagsNames);
        verify(questionTagRepository, times(2)).save(any(QuestionTag.class));
    }

    @Test
    public void deleteUselessQuestionTags_shouldInvokeMethodDeleteOnQuestionTagRepository() {
        //given
        doNothing().when(questionTagRepository).delete(any(QuestionTag.class));
        Set<QuestionTag> questionTagsSet = new HashSet<>(Arrays.asList(new QuestionTag("home"), new QuestionTag("holiday")));
        //when
        questionTagService.deleteUselessQuestionTags(questionTagsSet);
        //then
        verify(questionTagRepository, times(2)).delete(any(QuestionTag.class));
    }

    @Test
    public void findFlashcardsByTags_ShouldReturnFlashcardsListWithTwoElements_WhenQuestionTagExist() {
        //given
        when(questionTagRepository.findByName(anyString()))
                .thenReturn(Optional.of(super.createQuestionTag(1l, "home")));
        //when
        List<Flashcard> flashcardsFoundByTags = questionTagService.findFlashcardsByTags(tagsNamesList);
        //then
        Assert.assertEquals(2, flashcardsFoundByTags.size());
    }

    @Test
    public void findFlashcardsByTags_ShouldReturnEmptyList_whenQuestionTagDoesNotExist() {
        //given
        when(questionTagRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        //when
        List<Flashcard> flashcardsFoundByTags = questionTagService.findFlashcardsByTags(tagsNamesList);
        //then
        Assert.assertEquals(0, flashcardsFoundByTags.size());
    }
}