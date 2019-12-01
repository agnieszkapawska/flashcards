package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.FlashcardsApplicationAbstractTests;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class FlashcardServiceTest extends FlashcardsApplicationAbstractTests {
    @MockBean
    private FlashcardRepository flashcardRepository;
    @Autowired
    private FlashcardService flashcardService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInvokeMethodOnFlashcardRepository_WhenSaveFlashcard() {
        //given
        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion("question");
        ArgumentCaptor<Flashcard> flashcardArgumentCaptor = ArgumentCaptor.forClass(Flashcard.class);
        //when
        flashcardService.saveFlashcard(flashcard);
        //then
        verify(flashcardRepository, times(1)).save(flashcardArgumentCaptor.capture());
        Assert.assertEquals("question", flashcardArgumentCaptor.getValue().getQuestion());
    }

    @Test
    public void findById_ShouldReturnExpectedFlashcard_WhenFlashcardExists() {
        //given
        Flashcard flashcard = new Flashcard();
        flashcard.setId(1L);
        when(flashcardRepository.findById(anyLong()))
                .thenReturn(Optional.of(flashcard));
        //when
        Flashcard existingFlashcard = flashcardService.findById(1L);
        //then
        Assert.assertNotNull(existingFlashcard);
        Assert.assertTrue(existingFlashcard.getId()==1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findById_ShouldThrowEntityCouldNotBeFoundException_WhenFlashcardDoesNotExist() {
        //given
        doThrow(new EntityNotFoundException("Flashcard could't be found"))
                .when(flashcardRepository).findById(anyLong());
        //when
        flashcardService.findById(1L);
        //then -> expected throw exception
    }

    @Test
    public void findAll_ShouldReturnFlashcardList_WhenFlashcardsExist() {
        //given
        List<Flashcard> flashcardsList = Arrays.asList(new Flashcard(), new Flashcard());
        when(flashcardRepository.findAll()).thenReturn(flashcardsList);
        //when
        List<Flashcard> existingFlashcards = flashcardService.findAll();
        //then
        Assert.assertEquals(2,existingFlashcards.size());
        Assert.assertEquals(flashcardsList, existingFlashcards);
    }

    @Test
    public void findAll_ShouldReturnEmptyList_WhenFlashcardsDoNotExist() {
        //given
        when(flashcardRepository.findAll()).thenReturn(new ArrayList<>());
        //when
        List<Flashcard> existingFlashcards = flashcardService.findAll();
        //then
        Assert.assertNotNull(existingFlashcards);
        Assert.assertTrue(existingFlashcards.isEmpty());
    }

    @Test
    public void findByPhrase_ShouldReturnFlashcardsList_WhenFlashcardsContainSearchPhrase() {
        //given
        Optional<List<Flashcard>> flashcards = Optional.of(Arrays.asList(new Flashcard(), new Flashcard()));
        when(flashcardRepository.findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(flashcards);
        //when
        List<Flashcard> existingFlashcards = flashcardService.findByPhrase("search phrase");
        //then
        Assert.assertEquals(2,existingFlashcards.size());
    }

    @Test
    public void findByPhrase_ShouldReturnEmptyList_WhenFlashcardsDoNotContainSearchPhrase() {
        //given
        when(flashcardRepository
                .findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.empty());
        //when
        List<Flashcard> existingFlashcards = flashcardService.findByPhrase("search phrase");
        //then
        Assert.assertNotNull(existingFlashcards);
        Assert.assertTrue(existingFlashcards.isEmpty());
    }
}