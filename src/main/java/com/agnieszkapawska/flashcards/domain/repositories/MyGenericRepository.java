package com.agnieszkapawska.flashcards.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyGenericRepository<T> extends JpaRepository<T, Long> {
}
