package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.UserLoginDto;
import com.agnieszkapawska.flashcards.domain.dtos.UserSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.UserSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private UserFacade userFacade;

    @PostMapping("/registration")
    public ResponseEntity<UserSaveResponseDto> registerUser(@RequestBody UserSaveDto userSaveDto) {
        return new ResponseEntity<>(userFacade.registerUser(userSaveDto), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserSaveResponseDto> login(@RequestParam String username, @RequestParam String password) {
        return new ResponseEntity<>(userFacade.login(username, password), HttpStatus.OK);
    }
}
