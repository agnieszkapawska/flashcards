package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="flashcards_to_learn")
public class FlashcardsToLearn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(unique = true)
    private User user;
    @OneToMany(mappedBy = "flashcardsToLearn")
    private Set<Flashcard> flashcards;

    public FlashcardsToLearn(User user) {
        this.user = user;
    }
}
