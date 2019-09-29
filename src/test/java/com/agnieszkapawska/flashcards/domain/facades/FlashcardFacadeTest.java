package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.FlashcardAndQuestionTagAbstractTests;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.services.FlashcardService;
import com.agnieszkapawska.flashcards.domain.services.QuestionTagService;
import com.agnieszkapawska.flashcards.domain.services.authorization.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FlashcardFacadeTest extends FlashcardAndQuestionTagAbstractTests {
    @Autowired
    private FlashcardFacade flashcardFacade;
    @MockBean
    private FlashcardService flashcardService;
    @MockBean
    private QuestionTagService questionTagService;
    @MockBean
    private UserService userService;
    private Flashcard flashcard;

    @Before
    public void createFlashcard() {
        flashcard = new Flashcard();
        flashcard.setId(1L);
        flashcard.setQuestion("question");
        flashcard.setAnswer("answer");
        flashcard.setExplanation("explanation");
        //questionTag with flashcards
        QuestionTag questionTag = new QuestionTag();
        questionTag.setName("home");
        questionTag.setFlashcards(new HashSet<>(Collections.singletonList(new Flashcard("kubek", "mug"))));
        flashcard.getQuestionTagsSet().add(questionTag);
    }

    @Test
    public void saveFlashcard_ShouldReturnFlashcardSaveResponseDto() {
        //given
        FlashcardSaveDto flashcardSaveDto = new FlashcardSaveDto();

        Set<QuestionTag> questionTagSet = new HashSet<>(Arrays.asList(createQuestionTag(1L, "home"),
                createQuestionTag(2L, "holiday")));
        flashcardSaveDto.setTagsSet(new HashSet<>(Arrays.asList("home", "holiday")));
        flashcardSaveDto.setUserId(1L);
        when(userService.findById(anyLong()))
                .thenReturn(new User(1L ));
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
    public void whenUpdateFlashcard_ThenShouldInvokeDeleteUselessQuestionTagsInQuestionTagService_AndReturnExpectedFlashcardSaveResponseDto() {
        //given
        when(flashcardService.findById(anyLong()))
                .thenReturn(flashcard);
        when(questionTagService.getQuestionTagsSet(any()))
                //question tag to add
                .thenReturn(new HashSet<>(Collections.singletonList(new QuestionTag("holiday"))))
                //question tag to remove
                .thenReturn(new HashSet<>(Collections.singletonList(new QuestionTag("home"))));
        //when
        FlashcardSaveResponseDto flashcardSaveResponseDto = flashcardFacade.updateFlashcard(super.flashcardSaveDto, 1L);
        //then
        Assert.assertSame(1L, flashcardSaveResponseDto.getId());
        verify(questionTagService, times(1)).deleteUselessQuestionTags(any(HashSet.class));
    }

    @Test
    public void whenUpdateFlashcard_ThenShouldNotInvokeDeleteUselessQuestionTagsInQuestionTagService_AndReturnExpectedFlashcardSaveResponseDto() {
        //given
        //added question tag has two flashcards so shouldn't be remove
        flashcard.getQuestionTagsSet().add(super.questionTag);

        when(flashcardService.findById(anyLong()))
                .thenReturn(flashcard);
        when(questionTagService.getQuestionTagsSet(any()))
                //question tag to add
                .thenReturn(new HashSet<>())
                //question tag to remove
                .thenReturn(Collections.singleton(super.questionTag));
        //when
        FlashcardSaveResponseDto flashcardSaveResponseDto = flashcardFacade.updateFlashcard(super.flashcardSaveDto, 1L);
        //then
        Assert.assertSame(1L, flashcardSaveResponseDto.getId());
        verify(questionTagService, never()).deleteUselessQuestionTags(any(HashSet.class));
    }

    @Test
    public void getFlashcards() {
    }

    @Test
    public void getFlashcardById() {
    }
}