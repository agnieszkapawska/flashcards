package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.FlashcardAndQuestionTagAbstractTests;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.repositories.FlashcardRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FlashcardServiceTest extends FlashcardAndQuestionTagAbstractTests {
    @MockBean
    private FlashcardRepository flashcardRepository;
    @Autowired
    private FlashcardService flashcardService;

    @Test
    public void shouldInvokeMethodOnFlashcardRepository_WhenSaveFlashcard() {
        //given

        //when
        flashcardService.saveFlashcard(new Flashcard());
        //then
        verify(flashcardRepository, times(1)).save(any(Flashcard.class));
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
    public void findById_ShouldThrowEntityCouldNotBeFoundException_WhenFlashcardDoesNotExists() {
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
        when(flashcardRepository.findAll()).thenReturn(Arrays.asList(new Flashcard(), new Flashcard()));
        //when
        List<Flashcard> existingFlashcards = flashcardService.findAll();
        //then
        Assert.assertEquals(2,existingFlashcards.size());
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
        when(flashcardRepository.findAll()).thenReturn(Arrays.asList(new Flashcard(), new Flashcard()));
        //when
        List<Flashcard> existingFlashcards = flashcardService.findAll();
        //then
        Assert.assertEquals(2,existingFlashcards.size());
    }

    @Test
    public void findByPhrase_ShouldReturnEmptyList_WhenFlashcardsDoNotContainSearchPhrase() {
        //given
        when(flashcardRepository.findAll()).thenReturn(new ArrayList<>());
        //when
        List<Flashcard> existingFlashcards = flashcardService.findAll();
        //then
        Assert.assertNotNull(existingFlashcards);
        Assert.assertTrue(existingFlashcards.isEmpty());
    }
}