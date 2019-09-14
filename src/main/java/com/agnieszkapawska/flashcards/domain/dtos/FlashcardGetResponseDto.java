package com.agnieszkapawska.flashcards.domain.dtos;

import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardGetResponseDto {
    private String question;
    private Long id;
}
