package com.agnieszkapawska.flashcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="Flashcard")
public class Flashcard {
    @Id
    private Long id;
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
    private Set<QuestionTags> questionTagsList = new HashSet<>();
}
