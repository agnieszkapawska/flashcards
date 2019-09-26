package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="flashcards_to_learn")
public class FlashcardsToLearn extends FlashcardsStorage {
    @OneToMany
    @JoinColumn(name = "flashcardsToLearn_id")
    public Set<Flashcard> flashcards;

    public FlashcardsToLearn(User user) {
        super.user = user;
    }
}
