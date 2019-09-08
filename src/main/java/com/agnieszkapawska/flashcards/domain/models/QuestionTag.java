package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name="QuestionTag")
@NoArgsConstructor
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "questionTagsList")
    private Set<Flashcard> flashcards = new HashSet<>();

    public QuestionTag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        List<Long> flashcardsListIds = new ArrayList<>();
        for (Flashcard flashcard:flashcards) {
            flashcardsListIds.add(flashcard.getId());
        }
        return "QuestionTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flashcards=" + flashcardsListIds.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionTag that = (QuestionTag) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
