package com.agnieszkapawska.flashcards.domain.services;


import com.agnieszkapawska.flashcards.domain.models.Flashcard;
import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import com.agnieszkapawska.flashcards.domain.repositories.QuestionTagRepository;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Map;
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
            //change order ?
            questionTagRepository.save(questionTag);
            return questionTag;
        });
    }

    public void save(QuestionTag questionTag) {
        questionTagRepository.save(questionTag);
    }

    public void updateFlashcardSet(Map<String, Set<String>> tagsToUpdate, Flashcard flashcardFound) {
        for (String tagName:tagsToUpdate.get("tagsToRemove")) {
            Optional<QuestionTag> foundQuestionTagOptional = questionTagRepository.findByName(tagName);
            if(foundQuestionTagOptional.isPresent()) {
                QuestionTag questionTag = foundQuestionTagOptional.get();
                questionTag.getFlashcards().remove(flashcardFound);
                flashcardFound.getQuestionTagsList().remove(questionTag);
                if(questionTag.getFlashcards().isEmpty()) {
                    questionTagRepository.delete(questionTag);
                }
            }

        }
        for (String tagName:tagsToUpdate.get("tagsToAdd")) {
            Optional<QuestionTag> foundQuestionTagOptional = questionTagRepository.findByName(tagName);
            if(foundQuestionTagOptional.isPresent()) {
                QuestionTag questionTag = foundQuestionTagOptional.get();
                questionTag.getFlashcards().add(flashcardFound);
                flashcardFound.getQuestionTagsList().add(questionTag);
            } else {
                QuestionTag questionTag = new QuestionTag(tagName);
                questionTagRepository.save(questionTag);
                addFlashcardToSet(questionTag, flashcardFound);
                flashcardFound.getQuestionTagsList().add(questionTag);
            }
        }

    }
}
