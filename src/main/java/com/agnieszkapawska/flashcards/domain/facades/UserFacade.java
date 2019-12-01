package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.UserSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.UserRegistrationResponseDto;
import com.agnieszkapawska.flashcards.domain.exceptions.BadCredentialsException;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.services.authorization.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class UserFacade {
    private UserService userService;
    private ModelMapper modelMapper;

    public UserRegistrationResponseDto registerUser(UserSaveDto userSaveDto) {
        User user = modelMapper.map(userSaveDto, User.class);
        userService.save(user, userSaveDto.getRoles());
        return modelMapper.map(user, UserRegistrationResponseDto.class);
    }

    public UserRegistrationResponseDto login(String username, String password) {
        User user = userService.confirmCredentials(username, password)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
        return modelMapper.map(user, UserRegistrationResponseDto.class);
    }
}
