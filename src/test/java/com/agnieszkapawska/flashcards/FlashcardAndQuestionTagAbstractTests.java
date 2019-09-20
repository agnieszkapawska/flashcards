package com.agnieszkapawska.flashcards;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import org.junit.Before;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class FlashcardAndQuestionTagAbstractTests extends FlashcardsApplicationAbstractTests {
    protected FlashcardSaveDto flashcardSaveDto;
    protected FlashcardSaveResponseDto flashcardSaveResponseDto;
    protected QuestionTag questionTag;

    @Before
    public void setUp(){
        super.setUp();
        flashcardSaveDto = createFlashcardSaveDto();
        flashcardSaveResponseDto = new FlashcardSaveResponseDto();
        flashcardSaveResponseDto.setId(3L);
        questionTag = createQuestionTag(1L, "home");
    }

    public QuestionTag createQuestionTag(Long id, String name) {
        QuestionTag questionTag = new QuestionTag(name);
        questionTag.setId(id);

        Set<Flashcard> flashcardSet = new HashSet<>
                (Arrays.asList(new Flashcard("dom", "home"), new Flashcard("wakacje", "holiday")));
        questionTag.setFlashcards(flashcardSet);

        return questionTag;
    }

    public FlashcardSaveDto createFlashcardSaveDto() {
        FlashcardSaveDto flashcardSaveDto = new FlashcardSaveDto();
        flashcardSaveDto.setQuestion("question");
        flashcardSaveDto.setAnswer("answer");
        flashcardSaveDto.setTagsSet(new HashSet<>());
        return flashcardSaveDto;
    }
}
