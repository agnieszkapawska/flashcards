package com.agnieszkapawska.flashcards.domain.services;


import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import com.agnieszkapawska.flashcards.domain.utils.CompareQuestionTagsSets;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class QuestionTagService {
    private QuestionTagRepository questionTagRepository;

    public Set<QuestionTag> getQuestionTagsSet(Set<String> questionTagsName) {
        Set<QuestionTag> questionTagSet = new HashSet<>();
        for (String questionTagName:questionTagsName){
            QuestionTag questionTag = getQuestionTagIfPresentOrCreateNewIfNot(questionTagName);
            questionTagSet.add(questionTag);
        }
        return questionTagSet;
    }

    private QuestionTag getQuestionTagIfPresentOrCreateNewIfNot(String questionTagName) {
       return questionTagRepository.findByName(questionTagName).orElseGet(() -> {
            QuestionTag questionTag = new QuestionTag(questionTagName);
            //change order ?
            questionTagRepository.save(questionTag);
            return questionTag;
        });
    }

    public void save(QuestionTag questionTag) {
        questionTagRepository.save(questionTag);
    }

    public Set<QuestionTag> updateFlashcardSet(CompareQuestionTagsSets tagsToUpdate, Flashcard flashcard) {
        Set<QuestionTag> uselessQuestionTags = new HashSet<>();
        //remove
        tagsToUpdate.getTagsNamesToRemove().forEach(questionTagName -> {
            QuestionTag questionTag = questionTagRepository.findByName(questionTagName).orElseThrow(
                    () -> new EntityCouldNotBeFoundException("Question tag couldn't be found"));
            questionTag.getFlashcards().remove(flashcard);
            if(questionTag.getFlashcards().isEmpty()) {
              uselessQuestionTags.add(questionTag);
            }
        });
        //add
        tagsToUpdate.getTagsNamesToAdd().forEach(questionTagName -> {
            questionTagRepository.findByName(questionTagName).orElseGet(
                    () -> {
                        return questionTagRepository.save(new QuestionTag(questionTagName));
                    })
                    .getFlashcards().add(flashcard);
        });
        return uselessQuestionTags;
    }

    public void deleteUselessQuestionTags(Set<QuestionTag> questionTags) {
        questionTags.forEach(questionTag -> {
            questionTagRepository.delete(questionTag);
        });
    }
}
