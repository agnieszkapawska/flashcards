package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="flashcards_to_repeat")
public class FlashcardsToRepeat extends FlashcardsStorage {
    @OneToMany
    @JoinColumn(name = "flashcardsToRepeat_id")
    private Set<Flashcard> flashcards;

    public FlashcardsToRepeat(User user) {
        super.user = user;
    }
}
