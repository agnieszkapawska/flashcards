package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.FlashcardAndQuestionTagAbstractTests;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import java.util.stream.Collectors;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class QuestionTagServiceTest extends FlashcardAndQuestionTagAbstractTests {
    @MockBean
    private QuestionTagRepository questionTagRepository;
    @Autowired
    private QuestionTagService questionTagService;

    @Test
    public void getQuestionTagsSet_ShouldReturnTagsFromRepository_WhenTagsExist() {
        //given
        Set<String> tagsNames = new HashSet<>(Arrays.asList("home", "holidays"));
        when(questionTagRepository.findByName(any(String.class)))
                .thenReturn(Optional.of(super.questionTag));
        Set<QuestionTag> expectedQuestionTagSet = new HashSet<>(Arrays.asList(super.questionTag, super.questionTag));
        //when
        Set<QuestionTag> questionTagsSet = questionTagService.getQuestionTagsSet(tagsNames);
        //then
        Assert.assertEquals(expectedQuestionTagSet, questionTagsSet);
    }

    @Test
    public void getQuestionTagsSet_ShouldReturnCreatedTags_WhenTagDoNotExist() {
        //given
        Set<String> tagsNames = new HashSet<>(Arrays.asList("home", "holidays"));
        when(questionTagRepository.findByName(any(String.class)))
                .thenReturn(Optional.empty());
        //when
        Set<QuestionTag> questionTagsSet = questionTagService.getQuestionTagsSet(tagsNames);
        Set<String> newTagsNames = questionTagsSet.stream()
                .map(QuestionTag::getName)
                .collect(Collectors.toSet());
        //then
        Assert.assertEquals(2, questionTagsSet.size());
        Assert.assertEquals(tagsNames, newTagsNames);
    }

    @Test
    public void save() {
    }

    @Test
    public void deleteUselessQuestionTags() {
    }

    @Test
    public void findFlashcardsByTags() {
    }
}