package com.agnieszkapawska.flashcards.domain.models;

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
    private Set<QuestionTag> questionTagsSet = new HashSet<>();

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
}
