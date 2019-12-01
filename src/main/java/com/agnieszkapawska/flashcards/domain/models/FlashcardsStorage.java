package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Data
@MappedSuperclass
public class FlashcardsStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(unique = true)
    protected User user;
    @Transient
    private Set<Flashcard> flashcards;

    public FlashcardsStorage(User user) {
        this.user = user;
    }
}
