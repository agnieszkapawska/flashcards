package com.agnieszkapawska.flashcards.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="flashcards_to_refresh")
public class FlashcardsToRefresh extends FlashcardsStorage{
    @OneToMany
    @JoinColumn(name = "flashcardsToRefresh_id")
    private Set<Flashcard> flashcards;

    public FlashcardsToRefresh(User user) {
        super.user = user;
    }
}
