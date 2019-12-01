package com.agnieszkapawska.flashcards.domain.services.authorization;

import com.agnieszkapawska.flashcards.domain.models.User;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    void save(User user, Set<String> roles);
    User findByUsername(String username);
    User findById(Long id);
    Optional<User> confirmCredentials(String username, String password);
}
