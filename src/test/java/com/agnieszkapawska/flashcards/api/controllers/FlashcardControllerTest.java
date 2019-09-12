package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.FlashcardsApplicationAbstractTests;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class FlashcardControllerTest extends FlashcardsApplicationAbstractTests {

    @Test
    public void shouldReturnStatusOk_WhenSavingFlashcard() {
        //given
        when(super.flashcardFacade.saveFlashcard(any(FlashcardSaveDto.class))).thenReturn(super.flashcardSaveResponseDto);
        //when
        ResponseEntity<FlashcardSaveResponseDto> responseEntity =
                testRestTemplate.postForEntity(super.baseUrl + "/flashcard", super.flashcardSaveDto, FlashcardSaveResponseDto.class);
        //then
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(super.flashcardSaveResponseDto, responseEntity.getBody());
    }

    @Test
    public void shouldReturnStatusConflict_WhenSavingFlashcard_WhenCouldNotCreateEntity() {
        //given
        doThrow(new EntityNotCreatedException("something went wrong"))
                .when(super.flashcardFacade).saveFlashcard(any(FlashcardSaveDto.class));
        //when
        ResponseEntity<FlashcardSaveResponseDto> responseEntity =
                testRestTemplate.postForEntity(super.baseUrl + "/flashcard", super.flashcardSaveDto, FlashcardSaveResponseDto.class);
        //then
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}