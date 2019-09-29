package com.agnieszkapawska.flashcards;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class HelpersFactory extends FlashcardsApplicationAbstractTests {

    public static FlashcardSaveResponseDto createFlashcardSaveResponseDto() {
        FlashcardSaveResponseDto flashcardSaveResponseDto = new FlashcardSaveResponseDto();
        flashcardSaveResponseDto.setId(3L);
        return flashcardSaveResponseDto;
    }

    public static QuestionTag createQuestionTag() {
        return createQuestionTag(1L, "home");
    }

    public static QuestionTag createQuestionTag(Long id, String name) {
        QuestionTag questionTag = new QuestionTag(name);
        questionTag.setId(id);
        questionTag.setName("vary");

        Set<Flashcard> flashcardsSet = new HashSet<>
                (Arrays.asList(new Flashcard("dom", "home"), new Flashcard("wakacje", "holiday")));
        questionTag.setFlashcards(flashcardsSet);

        return questionTag;
    }

    public static FlashcardSaveDto createFlashcardSaveDto() {
        FlashcardSaveDto flashcardSaveDto = new FlashcardSaveDto();
        flashcardSaveDto.setQuestion("question");
        flashcardSaveDto.setAnswer("answer");
        flashcardSaveDto.setTagsSet(new HashSet<>());
        return flashcardSaveDto;
    }
}
