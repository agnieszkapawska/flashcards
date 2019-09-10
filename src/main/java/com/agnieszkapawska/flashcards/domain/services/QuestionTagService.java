package com.agnieszkapawska.flashcards.domain.services;


import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Optional;
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
            questionTagRepository.save(questionTag);
            return questionTag;
        });
    }

    public void save(QuestionTag questionTag) {
        questionTagRepository.save(questionTag);
    }
}
