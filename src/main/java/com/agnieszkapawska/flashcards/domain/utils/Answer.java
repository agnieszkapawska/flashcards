package com.agnieszkapawska.flashcards.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Answer {
    private Boolean isCorrect;
    private Long flashcardId;
    private Long userId;
}
