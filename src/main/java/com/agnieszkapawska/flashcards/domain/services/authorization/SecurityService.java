package com.agnieszkapawska.flashcards.domain.services;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
