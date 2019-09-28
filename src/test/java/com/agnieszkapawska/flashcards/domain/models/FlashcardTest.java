package com.agnieszkapawska.flashcards.domain.models;

import com.agnieszkapawska.flashcards.HelpersFactory;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import org.junit.Assert;
import org.junit.Test;

public class FlashcardTest extends HelpersFactory {

    @Test
    public void shouldUpdateFlashcard() {
        //given
        Flashcard flashcard = new Flashcard("dom", "home");
        FlashcardSaveDto flashcardSaveDto = new FlashcardSaveDto();
        flashcardSaveDto.setQuestion("chata");
        flashcardSaveDto.setAnswer("hut");
        flashcardSaveDto.setExampleUsage("I love my hut in forest");
        flashcardSaveDto.setExplanation("little home");
        //when
        flashcard.update(flashcardSaveDto);
        //then
        Assert.assertEquals("chata", flashcard.getQuestion());
        Assert.assertEquals("hut", flashcard.getAnswer());
        Assert.assertEquals("I love my hut in forest", flashcard.getExampleUsage());
        Assert.assertEquals("little home", flashcard.getExplanation());
    }

}