package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import lombok.AllArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void deleteUselessQuestionTags(Set<QuestionTag> questionTags) {
        questionTags.forEach(questionTag -> {
            questionTagRepository.delete(questionTag);
        });
    }

    public List<Flashcard> findFlashcardsByTags(List<String> tagsNames) {
        return tagsNames.stream()
                .map(tagName -> questionTagRepository.findByName(tagName))
                .flatMap(questionTag -> questionTag.isPresent() ? Stream.of(questionTag.get()) : Stream.empty())
                .map(QuestionTag::getFlashcards)
                .flatMap(questionTagsSet -> questionTagsSet.stream().distinct())
                .collect(Collectors.toList());
    }
}
