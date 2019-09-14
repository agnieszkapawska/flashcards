package com.agnieszkapawska.flashcards;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import org.junit.Before;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class FlashcardAndQuestionTagAbstractTests extends FlashcardsApplicationAbstractTests {
    protected FlashcardSaveDto flashcardSaveDto;
    protected FlashcardSaveResponseDto flashcardSaveResponseDto;
    protected QuestionTag questionTag;

    @MockBean
    protected FlashcardFacade flashcardFacade;

    @Before
    public void setUp(){
        super.setUp();
        flashcardSaveDto = new FlashcardSaveDto();
        flashcardSaveResponseDto = new FlashcardSaveResponseDto();
        flashcardSaveResponseDto.setId(3L);
        questionTag = createQuestionTag();
    }

    public QuestionTag createQuestionTag() {
        QuestionTag questionTag = new QuestionTag("home");
        questionTag.setId(1L);
        return questionTag;
    }
}
