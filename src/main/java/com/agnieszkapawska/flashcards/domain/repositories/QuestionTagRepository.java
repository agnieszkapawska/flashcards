package com.agnieszkapawska.flashcards.domain.repositories;

import com.agnieszkapawska.flashcards.domain.models.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
    Optional<QuestionTag> findByName(String name);
}
