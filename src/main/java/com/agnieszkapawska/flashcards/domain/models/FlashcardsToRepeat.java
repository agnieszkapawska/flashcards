package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="flashcards_to_repeat")
public class FlashcardsToRepeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(unique = true)
    private User user;
    @OneToMany
    @JoinColumn(name = "flashcardsToRepeat_id")
    private Set<Flashcard> flashcards;

    public FlashcardsToRepeat(User user) {
        this.user = user;
    }
}
