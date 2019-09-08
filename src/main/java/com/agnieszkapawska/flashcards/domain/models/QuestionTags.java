package com.agnieszkapawska.flashcards.domain.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="QuestionTag")
public class QuestionTags {
    @Id
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "questionTagsList")
    private Set<Flashcard> flashcards = new HashSet<>();
}
