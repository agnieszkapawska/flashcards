package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.FlashcardAndQuestionTagAbstractTests;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class FlashcardFacadeTest extends FlashcardAndQuestionTagAbstractTests {
    @Autowired
    private FlashcardFacade flashcardFacade;
    @MockBean
    private FlashcardService flashcardService;
    @MockBean
    private QuestionTagService questionTagService;

    @Test
    public void saveFlashcard_ShouldReturnFlashcardSaveResponseDto() {
        //given
        FlashcardSaveDto flashcardSaveDto = new FlashcardSaveDto();

        Set<QuestionTag> questionTagSet = new HashSet<>(Arrays.asList(createQuestionTag(1L, "home"),
                createQuestionTag(2L, "holiday")));
        flashcardSaveDto.setTagsSet(new HashSet<>(Arrays.asList("home", "holiday")));
        Flashcard flashcard = new Flashcard("imie", "name");
        flashcard.setId(1L);

        when(flashcardService.saveFlashcard(any(Flashcard.class)))
                .thenReturn(flashcard);
        when(questionTagService.getQuestionTagsSet(anySet()))
                .thenReturn(questionTagSet);

        Long expectedId = 1L;
        //when
        FlashcardSaveResponseDto flashcardSaveResponseDto = flashcardFacade.saveFlashcard(flashcardSaveDto);
        List<QuestionTag> questionTagList = new ArrayList<>(questionTagSet);
        //then
        Assert.assertNotNull(flashcardSaveResponseDto);
        Assert.assertEquals(expectedId, flashcardSaveResponseDto.getId());
        Assert.assertTrue(questionTagList.get(0).getFlashcards().contains(flashcard));
        Assert.assertTrue(questionTagList.get(1).getFlashcards().contains(flashcard));
    }

    @Test
    public void updateFlashcard() {
    }

    @Test
    public void getFlashcards() {
    }

    @Test
    public void getFlashcardById() {
    }
}