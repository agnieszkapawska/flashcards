package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.FlashcardAndQuestionTagAbstractTests;
import com.agnieszkapawska.flashcards.FlashcardsApplicationAbstractTests;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class FlashcardControllerTest extends FlashcardAndQuestionTagAbstractTests {
    private String url;
    @MockBean
    private FlashcardFacade flashcardFacade;

    @Before
    public void setUp() {
        super.setUp();
        url = FlashcardsApplicationAbstractTests.baseUrl + "/flashcards";
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingFlashcard() {
        System.out.println(url);
        //given
        when(flashcardFacade.saveFlashcard(any(FlashcardSaveDto.class))).thenReturn(super.flashcardSaveResponseDto);
        //when
        ResponseEntity<FlashcardSaveResponseDto> responseEntity =
                testRestTemplate.postForEntity(this.url, super.flashcardSaveDto, FlashcardSaveResponseDto.class);
        //then
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(super.flashcardSaveResponseDto, responseEntity.getBody());
    }

    @Test
    public void shouldReturnStatusConflict_WhenSavingFlashcard_WhenCouldNotCreateEntity() {
        System.out.println(url);
        //given
        doThrow(new EntityNotCreatedException("something went wrong"))
                .when(flashcardFacade).saveFlashcard(any(FlashcardSaveDto.class));
        //when
        ResponseEntity<FlashcardSaveResponseDto> responseEntity =
                testRestTemplate.postForEntity(this.url, super.flashcardSaveDto, FlashcardSaveResponseDto.class);
        //then
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}