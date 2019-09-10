package com.agnieszkapawska.flashcards.domain.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class CompareQuestionTagsSets {
    private Set<String> tagsNamesToRemove;
    private Set<String> tagsNamesToAdd;
}
