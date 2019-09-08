package com.agnieszkapawska.flashcards.domain.models;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardDto;
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
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Flashcard_QuestionTag",
            joinColumns = {@JoinColumn(name = "flashcard_id")},
            inverseJoinColumns = {@JoinColumn(name = "questionTag_id")}
    )
    private Set<QuestionTag> questionTagsList = new HashSet<>();

    @Override
    public String toString() {
        List<String> questionTagsNameList = new ArrayList<>();
        for (QuestionTag questionTag : questionTagsList) {
            questionTagsNameList.add(questionTag.getName());
        }
        return "Flashcard{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", exampleUsage='" + exampleUsage + '\'' +
                ", explanation='" + explanation + '\'' +
                ", questionTagsList=" + questionTagsList.toString() +
                '}';
    }

    public void setChanges(FlashcardDto flashcardDto) {
        this.setQuestion(flashcardDto.getQuestion());
        this.setAnswer(flashcardDto.getAnswer());
        this.setExampleUsage(flashcardDto.getExampleUsage());
        this.setExplanation(flashcardDto.getExplanation());
    }
}
