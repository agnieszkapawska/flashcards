package com.agnieszkapawska.flashcards.domain.dtos;

import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardSaveDto {
    private String question;
    private String answer;
    private String exampleUsage;
    private String explanation;
    private Set<String> tagsList;
}
