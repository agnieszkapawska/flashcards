package com.agnieszkapawska.flashcards.api.controllers;

import com.agnieszkapawska.flashcards.domain.dtos.UserSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.UserSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private UserFacade userFacade;

    @PostMapping("/registration")
    public ResponseEntity<UserSaveResponseDto> registerUser(@RequestBody UserSaveDto userSaveDto) {
        return new ResponseEntity<>(userFacade.registerUser(userSaveDto), HttpStatus.OK);
    }
}
