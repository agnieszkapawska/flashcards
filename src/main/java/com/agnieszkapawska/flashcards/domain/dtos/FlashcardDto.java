package com.agnieszkapawska.flashcards.domain.dtos;

import com.agnieszkapawska.flashcards.domain.models.QuestionTags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardDto {
    private String question;
    private String answer;
    private String exampleUsage;
    private String explanation;
    private Set<QuestionTags> questionTagsList;
}
