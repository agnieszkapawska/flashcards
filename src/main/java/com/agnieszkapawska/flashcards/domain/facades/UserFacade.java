package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.UserSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.UserSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.services.authorization.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class UserFacade {
    private UserService userService;
    private ModelMapper modelMapper;

    public UserSaveResponseDto registerUser(UserSaveDto userSaveDto) {
        User user = modelMapper.map(userSaveDto, User.class);
        userService.save(user, userSaveDto.getRoles());
        return modelMapper.map(user, UserSaveResponseDto.class);
    }

    public UserSaveResponseDto login(String username, String password) {
        User user = userService.confirmCredentials(username, password).orElse(new User());
        return modelMapper.map(user, UserSaveResponseDto.class);
    }
}
