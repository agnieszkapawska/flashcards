package com.agnieszkapawska.flashcards.domain.services;

import com.agnieszkapawska.flashcards.domain.models.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
