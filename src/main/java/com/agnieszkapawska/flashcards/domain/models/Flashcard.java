package com.agnieszkapawska.flashcards.domain.models;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void update(FlashcardSaveDto flashcardSaveDto) {
        this.setQuestion(flashcardSaveDto.getQuestion());
        this.setAnswer(flashcardSaveDto.getAnswer());
        this.setExampleUsage(flashcardSaveDto.getExampleUsage());
        this.setExplanation(flashcardSaveDto.getExplanation());
    }
}
