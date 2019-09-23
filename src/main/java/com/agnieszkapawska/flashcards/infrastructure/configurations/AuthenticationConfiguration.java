package com.agnieszkapawska.flashcards.infrastructure.configurations;

import com.agnieszkapawska.flashcards.domain.facades.UserFacade;
import com.agnieszkapawska.flashcards.domain.repositories.RoleRepository;
import com.agnieszkapawska.flashcards.domain.repositories.UserRepository;
import com.agnieszkapawska.flashcards.domain.services.authorization.UserService;
import com.agnieszkapawska.flashcards.domain.services.authorization.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthenticationConfiguration {
    @Bean
    UserServiceImpl userService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new UserServiceImpl(userRepository, roleRepository, bCryptPasswordEncoder);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserFacade userFacade(UserService userService, ModelMapper modelMapper) {
        return new UserFacade(userService, modelMapper);
    }
}
