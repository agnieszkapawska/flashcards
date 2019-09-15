package com.agnieszkapawska.flashcards.domain.models;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="Flashcard")
public class Flashcard {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    private String question;
    private String answer;
    private String exampleUsage;
    private String explanation;
    @ManyToMany
    @JoinTable(
            name = "Flashcard_QuestionTag",
            joinColumns = {@JoinColumn(name = "flashcard_id")},
            inverseJoinColumns = {@JoinColumn(name = "questionTag_id")}
    )
    @JsonManagedReference
    private Set<QuestionTag> questionTagsSet = new HashSet<>();

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void update(FlashcardSaveDto flashcardSaveDto) {
        this.setQuestion(flashcardSaveDto.getQuestion());
        this.setAnswer(flashcardSaveDto.getAnswer());
        this.setExampleUsage(flashcardSaveDto.getExampleUsage());
        this.setExplanation(flashcardSaveDto.getExplanation());
    }

    @Override
    public String toString() {
        List<String> questionTagsNameSet = new ArrayList<>();
        for (QuestionTag questionTag : questionTagsSet) {
            questionTagsNameSet.add(questionTag.getName());
        }

        return "Flashcard{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", exampleUsage='" + exampleUsage + '\'' +
                ", explanation='" + explanation + '\'' +
                ", questionTagsSet=" + questionTagsNameSet.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flashcard)) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(getId(), flashcard.getId()) &&
                Objects.equals(getQuestion(), flashcard.getQuestion()) &&
                Objects.equals(getAnswer(), flashcard.getAnswer()) &&
                Objects.equals(getExampleUsage(), flashcard.getExampleUsage()) &&
                Objects.equals(getExplanation(), flashcard.getExplanation()) &&
                Objects.equals(getQuestionTagsSet(), flashcard.getQuestionTagsSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestion());
    }
}
