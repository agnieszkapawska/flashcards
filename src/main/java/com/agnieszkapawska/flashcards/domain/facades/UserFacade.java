package com.agnieszkapawska.flashcards.domain.facades;

import com.agnieszkapawska.flashcards.domain.dtos.UserSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.UserSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.services.SecurityService;
import com.agnieszkapawska.flashcards.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class UserFacade {
    private UserService userService;
    private SecurityService securityService;
    private ModelMapper modelMapper;

    public UserSaveResponseDto registerUser(UserSaveDto userSaveDto) {
        User user = modelMapper.map(userSaveDto, User.class);
        userService.save(user);
        securityService.autoLogin(user.getUserName(), user.getPassword());
        return modelMapper.map(user, UserSaveResponseDto.class);
    }
}
