package com.agnieszkapawska.flashcards.domain.utils;

import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CompareQuestionTagsSets {
    private Set<String> tagsNamesToRemove;
    private Set<String> tagsNamesToAdd;

    public CompareQuestionTagsSets(Set<QuestionTag> questionTagsSetBeforeChanges, Set<String> questionTagsNamesSetActual) {
        HashSet<String> tagsNamesSetBeforeChanges = new HashSet<>();
        questionTagsSetBeforeChanges.forEach(questionTag -> tagsNamesSetBeforeChanges.add(questionTag.getName()));

        Set<String> tagsToRemove = Sets.difference(tagsNamesSetBeforeChanges, questionTagsNamesSetActual);
        Set<String> tagsToAdd = Sets.difference(questionTagsNamesSetActual, tagsNamesSetBeforeChanges);
        this.setTagsNamesToRemove(tagsToRemove);
        this.setTagsNamesToAdd(tagsToAdd);
    }
}
