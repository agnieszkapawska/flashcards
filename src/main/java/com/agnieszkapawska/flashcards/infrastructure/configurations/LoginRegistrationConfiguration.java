package com.agnieszkapawska.flashcards.infrastructure.configurations;

import com.agnieszkapawska.flashcards.domain.facades.UserFacade;
import com.agnieszkapawska.flashcards.domain.repositories.UserRepository;
import com.agnieszkapawska.flashcards.domain.services.SecurityService;
import com.agnieszkapawska.flashcards.domain.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class LoginRegistrationConfiguration {

    @Bean
    UserFacade userFacade(UserService userService, SecurityService securityService, ModelMapper modelMapper) {
        return new UserFacade(userService, securityService, modelMapper);
    }
}
