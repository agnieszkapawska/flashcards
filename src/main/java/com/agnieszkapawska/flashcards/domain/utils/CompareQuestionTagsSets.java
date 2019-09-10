package com.agnieszkapawska.flashcards.domain.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompareQuestionTagsSets {
    private String tagsToRemove;
    private String tagsToAdd;
}
